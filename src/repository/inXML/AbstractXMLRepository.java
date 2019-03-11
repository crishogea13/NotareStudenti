package repository.inXML;

import domain.HasID;
import exceptions.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import repository.inMemory.AbstractInMemoryRepository;
import validator.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public abstract class AbstractXMLRepository<ID, E extends HasID<ID>> extends AbstractInMemoryRepository<ID, E> {
    private String fileName;
    private DocumentBuilderFactory documentBuilderFactory;
    private TransformerFactory transformerFactory;

    public AbstractXMLRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.transformerFactory = TransformerFactory.newInstance();
        readFromXML();
    }

    protected abstract E createEntityFromElement(Element e);
    protected abstract Element createElementFromEntity(Document document, E entity);

    private void readFromXML() {
        try {
            Document document = documentBuilderFactory.newDocumentBuilder().parse(fileName);
            Element root = document.getDocumentElement();
            NodeList listaEntitati = root.getChildNodes();
            for(int i=0;i<listaEntitati.getLength();i++) {
                Node nod = listaEntitati.item(i);
                if(nod.getNodeType() == Node.ELEMENT_NODE)
                    super.save(createEntityFromElement((Element)nod));
            }

        } catch(Exception e) {};
    }

    private String getTagNameForRoot(){
        String[] split = fileName.split("\\/");
        split = split[split.length-1].split("\\.");
        return split[0].toLowerCase();
    }

    private void writeToXML(){
        try {
            Document document = documentBuilderFactory.newDocumentBuilder().newDocument();
            Element root = document.createElement(getTagNameForRoot());
            document.appendChild(root);
            findAll().forEach(entitate -> {
                Element element = createElementFromEntity(document,entitate);
                root.appendChild(element);
            });
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(fileName));
        } catch (Exception e) {};
    }


    protected Element createElementForEntity(Document document, String tagName, String info){
        Element e = document.createElement(tagName);
        e.setTextContent(info);
        return e;
    }

    @Override
    public E save(E entity) throws ValidationException {
        E e = super.save(entity);
        if(e==null)
            writeToXML();
        return e;
    }

    @Override
    public E delete(ID id) {
        E entityDeleted = super.delete(id);
        if(entityDeleted!=null)
            writeToXML();
        return entityDeleted;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if(e==null)
            writeToXML();
        return e;
    }
}

