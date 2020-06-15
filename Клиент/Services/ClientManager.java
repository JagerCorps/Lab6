package Services;

import CommunicationData.ElementsMessage;
import CommunicationData.FloatMessage;
import CommunicationData.Message;
import CommunicationData.StringMessage;

import java.util.Arrays;
import java.util.Comparator;


/**
 * Менеджер работы клиента
 */
public class ClientManager implements Serializer, Deserializer{

    /**
     * Пустой конструктор
     */
    public ClientManager(){}

    /**
     * Метод для вывода результата сообщения от сервера
     * @param message - сообщение от сервера
     */
    public void showResult(Message message){
        System.out.println(message.getMessageText());
        if (message instanceof ElementsMessage){
            Arrays.stream(((ElementsMessage) message).getOrganisations())
            .forEach(System.out::println);
        }
        else if (message instanceof FloatMessage){
            Arrays.stream(((FloatMessage) message).getTurnovers())
                    .sorted(Comparator.reverseOrder())
                    .forEach(System.out::println);
        }
        else if (message instanceof StringMessage){
            Arrays.stream(((StringMessage) message).getStrings())
                    .forEach(System.out::println);
        }
    }

}
