Attributes Forms Class Heirarchy
===

_Written by Samuel Rabinowitz_\
_Last updated on 4/27/2019_

### `AttributesPane`
The subclass of `AuthoringPane` that can be added to various places within the Authoring Environment.

### Building blocks
* `FormElement` (abstract) - the most basic piece of a form.
    * Subclasses `AuthoringPane`
    * Has the ability to show a delete button with a customizable action
    * Has a `packageData()` method that must be implemented by subclasses to export whatever data is in this form
* `FormList` (abstract) - a list of `FormElement`s.
    * Subclasses `FormElement`
    * Has the ability to show an add button with a customizable action
    * Manages added elements internally using `add`, `remove` and `iterate` methods
* `LabeledEditableFormList` (abstract) - a `FormList` with a title and add button at the top.
    * Subclasses `FormList`
* `LabeledTextField` - a label next to a text field.
    * Subclasses `FormElement`

### Agent Definition Form Structure
* `DefineAgentForm`
    * Save and Cancel buttons
    * `CommonAgentsFieldForm`
    * `AgentPropertiesForm`
        * List of `AgentPropertyFormElement`s
    * `ActionDecisionForm`
        * List of `ActionDecisionFormElement`s
            * `ActionFormElement` (subclass of `NameFieldsFormElement`)
                * List of `LabeledTextField` (parameters)
            * `ConditionsForm`
                * List of `ConditionFormElement`s (subclass of `NameFieldsFormElement`)
                    * List of `LabeledTextField` (parameters)                  