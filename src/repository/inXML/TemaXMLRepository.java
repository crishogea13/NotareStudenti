package repository.inXML;

import domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

public class TemaXMLRepository extends AbstractXMLRepository<Integer, Tema> {

    public TemaXMLRepository(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    protected Tema createEntityFromElement(Element e) {
        Integer temaID = Integer.parseInt(e.getAttribute("idTema"));
        String descriere = e.getElementsByTagName("descriere").item(0).getTextContent();
        Integer deadline = Integer.parseInt(e.getElementsByTagName("deadline").item(0).getTextContent());
        Integer saptPrimire = Integer.parseInt(e.getElementsByTagName("saptPrimire").item(0).getTextContent());
        return new Tema(temaID,descriere,deadline,saptPrimire);
    }

    @Override
    protected Element createElementFromEntity(Document document, Tema entity) {
        Element e = document.createElement("tema");
        e.setAttribute("idTema",entity.getID().toString());
        e.appendChild(createElementForEntity(document,"descriere",entity.getDescriere()));
        e.appendChild(createElementForEntity(document,"deadline",String.valueOf(entity.getDeadline())));
        e.appendChild(createElementForEntity(document,"saptPrimire",String.valueOf(entity.getSaptPrimire())));
        return e;
    }
}
