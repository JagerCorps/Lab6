package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Класс команды remove_all_by_full_name
 */
public class RemoveAllByFullName extends AbstractCommand implements Command {

    /**
     * Полное имя организации
     */
    protected String fullName;
    /**
     * Конструктор класса команды remove_all_by_full_name
     * @param organisations - коллекция объектов класса организации {@link Organisation}
     * @param fullName - полное имя
     */
    public RemoveAllByFullName(PriorityQueue<Organisation> organisations, String fullName){
        super(organisations);
        this.fullName = fullName;
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        int size = organisations.size();
        organisations = organisations.stream()
                .filter(o -> !o.getFullName().equals(fullName))
                .collect(Collectors.toCollection(PriorityQueue::new));
        if (size == organisations.size()){
            return new TextMessage("Элементы, подлежащие удалению, отсутствуют.");
        }
        else{
            return new TextMessage("Удаление элементов произошло успешно.");
        }
    }
}
