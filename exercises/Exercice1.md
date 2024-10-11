# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

A refresher on TCC and LCC is available in the [course notes](https://oscarlvp.github.io/vandv-classes/#cohesion-graph).

## Answer

**TCC vs LCC :** 

Tight Class Cohesion (TCC) and Loose Class Cohesion (LCC) are two metrics used to measure how cohesive a class is, in terms of the relationships between its methods and attributes. 

 Circumstances Where TCC Equals LCC
TCC and LCC will produce the same value when:
- All pairs of methods that could potentially be connected are directly connected through shared attributes. 
- In other words, there are no indirect connections between methods that are not already captured by the direct connections.

`Example :`

![BankAccount](https://github.com/user-attachments/assets/f26043cb-0022-451a-ab1d-0089293b1d47)

  


In this case:
- All methods (`deposit()`, `withdraw()`, `getBalance()`) interact directly with the `balance` attribute.
- Therefore, every pair of methods is directly connected via the `balance` attribute.

Since all methods are directly connected through the shared attribute `balance`, TCC = LCC. There's no indirect method pair connection that would only be counted by LCC but not by TCC.

`TCC (Tight Class Cohesion) : Connexions directes :`

For TCC,  we look for direct connexions : 
BankAccount(double initialBalance) modifies balance directly.
deposit(double amount) modifies balance directly.
withdraw(double amount) modifies balance directly.
getBalance() can access directly to balance.

**Conclusion for TCC** : All methods share directly the attribute balance. So, all the pairs are directly connected.
TCC=6 possible pairs / 6 direct connexions ​= 1
 
`LCC (Loose Class Cohesion) : Connexions directes et indirectes :`

LCC uses both direct and indirect connections. However in this class there is no indirect connection, because all methods are already directly connected via the attribute Balance.
so this means that LCC = TCC = 1.



**Could LCC Be Lower Than TCC?**
No, LCC cannot be lower than TCC for any class because TCC only counts direct connections between method pairs, while LCC includes both direct and indirect connections. 

The LCC will either:
- Be equal to TCC if all connections are direct (as shown in the example above).
- Be greater than TCC if there are indirect connections that are not captured by TCC.
