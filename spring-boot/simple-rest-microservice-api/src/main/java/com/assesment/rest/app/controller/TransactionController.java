package com.assesment.rest.app.controller;

import com.assesment.rest.app.model.Transaction;
import com.assesment.rest.app.model.Transactions;
import com.assesment.rest.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value="/transactions/insert", consumes=MediaType.APPLICATION_XML_VALUE)
    public HttpEntity<? extends Object> insert(@Validated @RequestBody Transactions transactions) {
        transactionService.insert(transactions);

        try {
            if (transactions.getTransaction().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(transactions.getTransaction().size()+" transactions inserted sucessfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/transactions/insertBulk", consumes=MediaType.APPLICATION_XML_VALUE)
    public HttpEntity<? extends Object> insertBulk(InputStream is) throws ParserConfigurationException, IOException, SAXException, TransformerException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(is);

        Transactions transactions = new Transactions();
        Transaction transaction =null;
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "Transaction":
                        transaction  = new Transaction();
                        break;
                    case "id":
                        nextEvent = reader.nextEvent();
                        transaction.setId(Long.parseLong(nextEvent.asCharacters().getData()));
                        break;
                    case "account":
                        nextEvent = reader.nextEvent();
                        transaction.setAccount(nextEvent.asCharacters().getData());
                        break;
                    case "amount":
                        nextEvent = reader.nextEvent();
                        transaction.setAmount(Double.parseDouble(nextEvent.asCharacters().getData()));
                        break;
                    case "comments":
                        nextEvent = reader.nextEvent();
                        transaction.setComments(nextEvent.asCharacters().getData());
                        break;
                }
            }
            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("Transaction")) {
                    transactions.getTransaction().add(transaction);
                }
            }
        }
        // IT can be called iteratively after specific count if required
        transactionService.insert(transactions);

        try {
            if (transactions.getTransaction().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(transactions.getTransaction().size()+" transactions inserted sucessfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
