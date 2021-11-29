package de.ac.money.ruebentool.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import de.ac.money.ruebentool.RuebenToolApplication;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.Configurations.consideringOnlyDependenciesInDiagram;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.adhereToPlantUmlDiagram;

@AnalyzeClasses(packagesOf = RuebenToolApplication.class)
public class ArchUnitTests {
private final JavaClasses klassen = new ClassFileImporter().importPackagesOf(RuebenToolApplication.class);

    @Test
    @DisplayName("UML Architecture is implemented")
    public void test1() {
        ArchRule rule1 = classes()
                .should(adhereToPlantUmlDiagram("architecture.puml",
                        consideringOnlyDependenciesInDiagram()));
        rule1.check(klassen);
    }

    @Test
    @DisplayName("Nur Konstruktor-Injection")
    public void test2() {
        ArchRule rule2 = members().should().notBeAnnotatedWith(Autowired.class);
        rule2.check(klassen);
    }
    @Test
    @DisplayName("Domain hat korrekte Annotationen")
    public void test3() {
        ArchRule rule3 = classes()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .notBeAnnotatedWith("Service")
                .andShould()
                .notBeAnnotatedWith("Component")
                .andShould()
                .notBeAnnotatedWith("Repository");
        rule3.check(klassen);
    }




}
