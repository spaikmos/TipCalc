# Week 1 Project:  Tip Calculator

This is a basic tip calculator.

Time spent:  6 hours total.

Completed user stories:

 * [x] Required: User is displayed the tip of specified percentage for specified entered amount
 * [x] Required: User enters the total amount of the transaction
 * [x] Required: User can select between tip amounts (i.e 10%, 15%, 20%)
 * [x] Required: Upon selecting tip amount, formatted tip value is displayed
 * [x] Optional: User changes the total amount and updated tip is reflected automatically
 * [x] Optional: User can select custom tip percentage if desired
 * [x] Optional: User can select how many ways to split the tip
 * [ ] Optional: User can edit preset tip percentages and have them persist across launches
 * [ ] Optional: Experiment with trying input widgets to replace the buttons and/or textviews
 * [ ] Optional: Improve the user interface and experience by using images and/or colors

Notes:

Decided to change the dollar sign icon after initial project creation.  Wasn't
as bad as I thought it would be.

Spent some time trying the number picker widget, but couldn't get it to work
properly.  Arrows never showed up in the emulator nor on a real device.

Tried changing the layout to add some margins to the border of the screen, but
that totally messed things up.  Decided to revert and live with a crappy UI.


Todo:

The UI could be greatly simplified, requiring only a single input (bill amount)
from the user.  From this input, a table of tips from 1% - 25% could be 
generated automatically, allowing the user to choose the tip he wishes.  This
is especially useful because it would allow the user to round up/down the tip
amount, and guesstimate how much the actual tip is.  

For instance, let's say user's bill is $8, and he sees that a 15% tip brings
the total to $9.20.  He can decide whether to leave $9, $9.50, $10.00, etc 
depending on what combination of bills and pocket change he has.

Furthermore, the tip table could highlight the range between 15% and 20% to
show the user what a typically socially acceptable tip is.

To calculate splits, we could use left/right gestures to increment / decrement
the number of parties to split by.  This would be much simpler than entering
a number via the edit text view.

Finally, we could add a persistent database that keeps track of history.  User
would input the establishment that he visited, and the app would record a 
timestamp, bill amount, selected tip, etc.  User could then use a drop down
menu when entering the establishment to pick a recently used one or a new one,
and he could download the data to a google sheets document.

Walkthrough of all user stories:

![Video Walkthrough](TipCalcDemo.gif)