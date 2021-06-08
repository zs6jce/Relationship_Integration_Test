package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.ChildEntity;
import com.example.demo.model.ParentEntity;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.repositories.ParentRepository;

@DataJpaTest
public class RelationshipIT {

	@Autowired
	private ParentRepository parentRepo;

	@Autowired
	private ChildRepository childRepo;

	String NAME_PARENT = "Parent";
	String NAME_CHILD_A = "Child A";
	String NAME_CHILD_B = "Child B";

	@Test
	public void test() {

		// ------------------------------------------------
		// Create Parent
		ParentEntity newParent;
		ParentEntity savedParent;
		// Given
		newParent = new ParentEntity();
		newParent.setName(NAME_PARENT);
		// When
		savedParent = parentRepo.save(newParent);
		// Then
		assertThat(savedParent.getName()).isEqualTo(NAME_PARENT);
		assertThat(savedParent.getChildren()).isEmpty();

		// ------------------------------------------------
		// Create Child A
		ChildEntity newChildA;
		ChildEntity savedChildA;
		// Given
		newChildA = new ChildEntity();
		newChildA.setName(NAME_CHILD_A);
		newChildA.setParent(savedParent);
		// When
		savedChildA = childRepo.save(newChildA);
		// Then
		assertThat(savedChildA.getName()).isEqualTo(NAME_CHILD_A);
		assertThat(savedChildA.getParent()).isEqualTo(savedParent);

		// ------------------------------------------------
		// Create Child B
		ChildEntity newChildB;
		ChildEntity savedChildB;
		// Given
		newChildB = new ChildEntity();
		newChildB.setName(NAME_CHILD_B);
		newChildB.setParent(savedParent);
		// When
		savedChildB = childRepo.save(newChildB);
		// Then
		assertThat(savedChildB.getName()).isEqualTo(NAME_CHILD_B);
		assertThat(savedChildB.getParent()).isEqualTo(savedParent);

		// ------------------------------------------------
		/* 
		 * 1) Validate that the @OneToMany relationship retrieves the set of children
		 * 2) Validate that orphanRemoval does a cascade delete of children when parent is deleted
		 */
		
		// Given
		ParentEntity foundParent = parentRepo.findById(savedParent.getId()).get();
		Set<ChildEntity> foundChildren = foundParent.getChildren();
		assertThat(foundChildren).isNotEmpty(); // FIXME - The list of children should not be empty.
		assertThat(foundChildren.contains(savedChildA)).isTrue();
		assertThat(foundChildren.contains(savedChildB)).isTrue();
		// When
		parentRepo.deleteById(foundParent.getId());
		// Then
		assertThat(parentRepo.findById(savedParent.getId()).get()).isNull();
		assertThat(childRepo.findById(savedChildA.getId()).get()).isNull();
		assertThat(childRepo.findById(savedChildB.getId()).get()).isNull();
	}

}
