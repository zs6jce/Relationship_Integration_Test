# Relationship_Integration_Test
Relationship_Integration_Test

Failing Integration Test demonstrating the issue
Line 82 - https://github.com/zs6jce/Relationship_Integration_Test/blob/main/src/test/java/com/example/demo/RelationshipIT.java

The Issue:
	After adding a new Parent (@OneToMany) and some Children (@ManyToOne).
	Fetching the Parent the Set<Child> is empty.

Request:
	Why is does the parent entity not contain the listing of children after re-fetching from the repo?
