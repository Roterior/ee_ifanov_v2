package com.accenture.flowershop.config;

import com.accenture.flowershop.be.access.ClientDAO;
import com.accenture.flowershop.be.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Enumeration;

@Component
public class RequestListener extends Thread {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void run() {
        while (true) {
            getNewDiscount();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getNewDiscount() {
        Client clientQueue = null;

        try {
            if (getQueueSize() > 0) {
                JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
                String queueName = "IN_QUEUE";
                Message message = jmsTemplate.receive(queueName);
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();

                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(Client.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    clientQueue = (Client) jaxbUnmarshaller.unmarshal(new StringReader(text));

                    Client client = clientDAO.getByLogin(clientQueue.getLogin());
                    if (client != null) {
                        client.setDiscount(clientQueue.getDiscount());
                        clientDAO.update(client);
                    }
                }
                catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JMSException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private int getQueueSize() {
        Connection connection = null;
        Session session = null;
        int count = 0;

        try {
            connection = connectionFactory.createConnection("admin", "admin");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue destination = session.createQueue("IN_QUEUE");
            QueueBrowser browser = session.createBrowser(destination);
            Enumeration elems = browser.getEnumeration();

            while (elems.hasMoreElements()) {
                elems.nextElement();
                count++;
            }
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            // Ignore
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }
}
