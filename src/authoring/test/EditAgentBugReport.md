## Description

The current implementation of our code has a bug when it comes to editing agents that were already defined. It always says the agent was already defined.

## Expected Behavior

The agent is edited.

## Current Behavior

Same-name checking is applied and doesn't allow editing.

## Steps to Reproduce

 1. Open Authoring and create an agent.
 2. Save the agent.
 3. Click the down arrow under the agent and click Edit.
 4. Edit the agent (or not) and click save.
 5. Authoring console output notes that agent was not edited.

## Failure Logs

There are no stack traces that display. The error is only evident by looking at the Authoring Environment.

## Fix for Bug

Rectifying this bug is done by moving the editing logic one level higher in the packageData() tree, since that is the place where a boolean is present to indicate whether or not it is in editing mode.