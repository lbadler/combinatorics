This repository contains code used in WPI's MA2273 combinatorics.
In general, all code will be added to CombinatoricsStuff.java, with snippets for submission of individual questions residing in their own files.

Some notes on the partition function files:
PartitionFunction.java is the original, but had a significant ineffeciency causing it to take several orders of magnitude longer than it should have
PartitionFunctionInt and PartitionFunctionLong maintain this inefficiency, but operate on small enough scale for its effect to be minor
The Int and Long versions of the program serve to demonstrate how quickly p(n) overflows various number datatypes
PartitionFunctionV2 corrects the inefficiency of the original, allowing calculating p(0) to p(10000) in under two minutes, when the original took nearly two days