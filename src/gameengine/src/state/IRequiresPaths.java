package state;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public interface IRequiresPaths {
    void injectPaths(Map<String, List<Point2D>> paths);
}
