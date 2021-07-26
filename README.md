# Tree-Structure-Stock-Values-

> There are three types of queries to the data structure:

1. insert a stock with a certain gain/loss value
2. flip all the gains to losses and losses to gains by toggling the negative sign, for all stocks in a given range a, b
3. return the current gain/loss value of a given stock

------------------

Input Format

The first line contains n the number of queries to be made to the database.

The next n lines contain queries of the following 3 types

1 a k, insert a stock with current parameter k

2 a b, toggle the parameters for all stocks between a, b inclusive

3 a, return the current value of the parameter for a stock a


------------------

Output Format

For each query of type 3 print the parameter of the stock in a new line.

If the stock is not in the database, print a "-1", without the quotes.
