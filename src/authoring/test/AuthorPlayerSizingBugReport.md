## Description

The current implementation of our code has a bug when it comes to sizing our Authoring and Player environments. Although each works in it's own right, having different map sizes that affect where agents can be placed causes an irreconcilable inconsistency.

## Expected Behavior

Authoring and Player have the same sizes for level.

## Current Behavior

Authoring and Player have different sizes for level.

## Steps to Reproduce

 1. Inspect the InsetMapWidth and InsetMapHeight properties in authoring/data/strings/English.properties.
 2. Inspect the WIDTH and HEIGHT constants in player/Level/Level.java.
 3. Note that they are different.
 4. Note that neither are equal to our agreed width and height.

## Failure Logs

There are no stack traces that display. The error is only evident visually.

## Fix for Bug

Rectifying this bug is as simple as agreeing on a size (which we did) and changing the values.