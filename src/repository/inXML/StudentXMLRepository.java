package repository.inXML;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

import javax.print.Doc;

public class StudentXMLRepository extends AbstractXMLRepository<String, Student> {

    public StudentXMLRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    protected Student createEntityFromElement(Element e) {
        String studentID = e.getAttribute("idStudent");
        String nume = e.getElementsByTagName("nume").item(0).getTextContent();
        String grupa = e.getElementsByTagName("grupa").item(0).getTextContent();
        String email = e.getElementsByTagName("email").item(0).getTextContent();
        String cadruDidactic = e.getElementsByTagName("cadruDidactic").item(0).getTextContent();
        return new Student(studentID,nume,grupa,email,cadruDidactic);
    }

    @Override
    protected Element createElementFromEntity(Document document, Student entity) {
        Element e = document.createElement("student");
        e.setAttribute("idStudent",entity.getID());
        e.appendChild(createElementForEntity(document,"nume",entity.getNume()));
        e.appendChild(createElementForEntity(document,"grupa",entity.getGrupa()));
        e.appendChild(createElementForEntity(document,"email",entity.getEmail()));
        e.appendChild(createElementForEntity(document,"cadruDidactic",entity.getCadruDidactic()));
        return e;
    }
}
