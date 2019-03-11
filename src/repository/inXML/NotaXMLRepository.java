package repository.inXML;

import domain.Nota;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

public class NotaXMLRepository extends AbstractXMLRepository<Pair<String, Integer>, Nota> {

    public NotaXMLRepository(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    protected Nota createEntityFromElement(Element e) {
        String studentID = e.getAttribute("idStudent");
        Integer temaID = Integer.parseInt(e.getAttribute("idTema"));
        Double valoare = Double.parseDouble(e.getElementsByTagName("valoare").item(0).getTextContent());
        Integer saptamanaPredare = Integer.parseInt(e.getElementsByTagName("saptamanaPredare").item(0).getTextContent());
        return new Nota(studentID,temaID,valoare,saptamanaPredare);
    }

    @Override
    protected Element createElementFromEntity(Document document, Nota entity) {
        Element e = document.createElement("nota");
        e.setAttribute("idStudent",entity.getIdStudent());
        e.setAttribute("idTema",entity.getIdTema().toString());
        e.appendChild(createElementForEntity(document,"valoare",entity.getValoare().toString()));
        e.appendChild(createElementForEntity(document,"saptamanaPredare",entity.getSaptamanaPredare().toString()));
        return e;
    }

}
