# project26

## Team Members:

* Tianwei Wang
* Da Li
* Tiancheng Zhang
  

## User stories

1. A bank customer should be able to deposit into an existing account. (Shook)
2. A bank customer should be able to withdraw from an account.(Tiancheng Zhang) 
3. A bank customer should be able to check their account balance. (Tianwei Wang)
4. A bank customer should be able to view their transaction history for an account. (x)
5. A bank customer should be able to create an additional account with the bank. (Da)
6. A bank customer should be able to close an existing account.(Tiancheng Zhang)
7. A bank customer should be able to transfer money from one account to another. (Da)
8. A bank adminstrator should be able to collect fees from existing accounts when necessary.(Tianwei Wang)
9. A bank adminstrator should be able to add an interest payment to an existing account when necessary. (Tianwei Wang)
10. A bank customer should be able to receive an error message when performing an invalid operation. (Tiancheng Zhang)

## What user stories do you intend to complete next iteration?

We plan to extend the current system beyond basic deposit functionality. Specifically, we aim to add support for multiple accounts, account creation, withdrawals, balance checking, and money transfer between accounts.
We intend to complete user stories related to account security, account types, and improved account management in the next iteration.

## Is there anything that you implemented but doesn't currently work?

At this stage, all implemented features are working as expected. The system correctly supports deposits and handles invalid inputs by throwing exceptions.

However, the application is still limited in functionality and does not yet support additional banking features such as transfers or multiple accounts.

## What commands are needed to compile and run your code from the command line?

From the src directory, run:

javac main/*.java test/*.java

java main.MainMenu
