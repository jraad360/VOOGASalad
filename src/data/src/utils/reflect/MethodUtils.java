package utils.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Both of these methods are slower alternatives to object.class.getMethod(...) because small discrepancies
 * such as proxies and type erasure make that method return NoSuchMethodExceptions.
 * Additionally, .equals() does not work due to the proxy, which was the reason for isMethodInList.
 */
public class MethodUtils {

    /**
     * Iterates through the list of methods for an object for this provided method, and checks if there is a method
     * with the same name, number of parameters, and has parameter types that accept our arguments.
     * @param method Method we are checking if it is in this list
     * @param methods Array of methods declared for an object
     * @return If an acceptable method is in this list
     */
    public static boolean isMethodInList(Method method, Method[] methods) {
        return Arrays.stream(methods).anyMatch(arrMethod -> namesAndLengthsMatch(arrMethod, method.getName(), method.getParameterCount()) &&
                typesAreChildren(arrMethod, method.getParameterTypes()));
    }

    /**
     * In the case that finding a method which directly works
     * @param methodName Name of the method we are trying to find
     * @param argumentTypes Class types of each of the parameters
     * @param methods Array of methods declared for an object
     * @return
     */
    public static Method findMethodByNameAndArgs(String methodName, Class<?>[] argumentTypes, Method[] methods) {
        List<Method> possibleMethods = Arrays.stream(methods)
                .filter((method -> namesAndLengthsMatch(method, methodName, argumentTypes.length) &&
                        typesAreChildren(method, argumentTypes)))
                .collect(Collectors.toList());
        return (possibleMethods.size() > 0) ? possibleMethods.get(0) : null;
    }

    private static boolean typesAreChildren(Method method, Class<?>[] argumentTypes) {
        Class<?>[] methodClassTypes = method.getParameterTypes();
        for (int i = 0; i < methodClassTypes.length; i++) {
            if (!methodClassTypes[i].isAssignableFrom(argumentTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean namesAndLengthsMatch(Method m1, String m2Name, int m2ArgCt) {
        return m1.getName().equals(m2Name) && m1.getParameterCount() == m2ArgCt;
    }
}
