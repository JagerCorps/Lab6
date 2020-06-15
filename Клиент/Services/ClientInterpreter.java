package Services;

import Commands.*;
import CommunicationData.ClientCommand;
import Data.Organisation;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClientInterpreter {

    /**
     * Сканер. Если команды не будут являтся прочитанными из скрипта
     * Значения полей будут считываться с его помощью
     * Подразумевается, что он будет привязан к консоли
     */
    protected Scanner scanner;

    /**
     * Конструктор интерпретатора
     * @param scanner - сканер
     */
    public ClientInterpreter(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Метод для создания нового элемента при вызове определённых команд
     * clientCommand =  возвращает элемент класса Organisation (без id)
     */
    public Organisation newElement(boolean isScript, String line){
        Organisation newOrg = new Organisation();
        if (!isScript){
            newOrg.setName(FieldReaders.readString(scanner, "Введите имя:"));
            newOrg.setCoordinates(FieldReaders.readX(scanner), FieldReaders.readY(scanner));
            newOrg.setCreationDate(LocalDate.now());
            newOrg.setAnnualTurnover(FieldReaders.readTurnover(scanner));
            newOrg.setFullName(FieldReaders.readString(scanner, "Введите полное имя:"));
            newOrg.setEmployeesCount(FieldReaders.readInt(scanner, "Введите количество сотрудников:"));
            newOrg.setType(FieldReaders.readType(scanner));
            newOrg.setOfficialAddress(FieldReaders.readString(scanner, "Введите официальный адрес организации:"));

            return newOrg;
        }
        else {
            String newLine = line.replaceAll("[{}\\s]", " ");
            String[] values = newLine.split("; ");

            String name;
            double x = 0;
            double y = 0;
            LocalDate creationDate = LocalDate.now();
            float annualTurnover = 0;
            String fullName;
            int employeesCount = 0;
            String organisationType;
            String address;

            boolean dataIsCorrect = true;

            name = values[0].trim();
            if (name.isEmpty()) {
                System.out.println("Возникла проблема. Имя не может быть пустым.");
                dataIsCorrect = false;
            }
            try {
                x = Double.parseDouble(values[1]);
                if (x < -422) {
                    System.out.println("Возникла проблема. Координата Х не может быть меньше -422.");
                    dataIsCorrect = false;
                }
            } catch (Exception e) {
                System.out.println("Возникла проблема с координатой X.");
                dataIsCorrect = false;
            }

            try {
                y = Double.parseDouble(values[2]);
            } catch (Exception e) {
                System.out.println("Возникла проблема с координатой Y.");
                dataIsCorrect = false;
            }
            try {
                creationDate = LocalDate.parse(values[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (Exception e) {
                System.out.println("Возникла проблема с датой.");
                dataIsCorrect = false;
            }
            try {
                annualTurnover = Float.parseFloat(values[4]);
                if (annualTurnover < 0) {
                    System.out.println("Возникла проблема. Годовой оборот не может быть меньше 0");
                    dataIsCorrect = false;
                }
            } catch (Exception e) {
                System.out.println("Возникла проблема с годовым оборотом.");
                dataIsCorrect = false;
            }
            fullName = values[5];
            if (fullName.isEmpty()) {
                System.out.println("Возникла проблема. Полное имя не может быть пустым");
                dataIsCorrect = false;
            }
            try {
                employeesCount = Integer.parseInt(values[6]);
                if (employeesCount < 0) {
                    System.out.println("Возникла проблема. Количество сотрудников не может быть меньше 0");
                    dataIsCorrect = false;
                }
            } catch (Exception e) {
                System.out.println("Возникла проблема с количеством сотрудников.");
                dataIsCorrect = false;
            }
            organisationType = values[7];
            address = values[8].trim();
            System.out.println(address);
            if (address.isEmpty()) {
                System.out.println("Возникла проблема. Адрес не может быть пустым");
                dataIsCorrect = false;
            }
            newOrg.setName(name);
            newOrg.setCoordinates(x, y);
            newOrg.setCreationDate(creationDate);
            newOrg.setAnnualTurnover(annualTurnover);
            newOrg.setFullName(fullName);
            newOrg.setEmployeesCount(employeesCount);
            try {
                newOrg.setType(organisationType);
            } catch (IllegalArgumentException e) {
                System.out.println("Возникла проблема. Тип организации введён некорректно");
                dataIsCorrect = false;
            }
            newOrg.setOfficialAddress(address);
            if (dataIsCorrect) {
                return newOrg;
            } else {
                System.out.println("Исправьте скрипт.");
                return null;
            }
        }
    }


    /**
     * Метод, считывающий строку
     * @param inputCommand - строка с командой
     */
    public ClientCommand readLine(String inputCommand, boolean isScript){
        String[] command = inputCommand.split("[\\s]", 2);
        ClientCommand clientCommand = new ClientCommand(CommandType.null_command);
        switch (command[0]){
            case "help":
                clientCommand = new ClientCommand(CommandType.help);
                break;

            case "info":
                clientCommand = new ClientCommand(CommandType.info);
                break;

            case "show":
                clientCommand = new ClientCommand(CommandType.show);
                break;

            case "add":
                Organisation element = this.newElement(isScript,"");
                if (element.equals(null)){
                    //let the command be null_command
                }
                else{
                    clientCommand = new ClientCommand(CommandType.add, element);
                }

                break;

            case "update":
                Organisation updatedElement = this.newElement(isScript,"");
                if (updatedElement.equals(null)){
                    //let the command be null_command
                }
                else {
                    int id;
                    if(isScript){
                        try {
                            id = Integer.parseInt(command[1]);
                            if (id < 0) {
                                System.out.println("Возникла проблема. Id не может быть меньше 0.");
                                //let the command be null_command
                            }
                            else{
                                clientCommand = new ClientCommand(CommandType.update, id, updatedElement);
                            }
                        } catch (Exception e) {
                            System.out.println("Возникла проблема с id.");
                            //let the command be null_command
                        }
                    }
                    else{
                        clientCommand = new ClientCommand(CommandType.update,
                                FieldReaders.readInt(scanner,"Введите id элемента:"), updatedElement);
                    }
                }
                break;

            case "remove_by_id":
                int id;
                if(isScript){
                    try {
                        id = Integer.parseInt(command[1]);
                        if (id < 0) {
                            System.out.println("Возникла проблема. Id не может быть меньше 0.");
                            //let the command be null_command
                        }
                        else{
                            clientCommand = new ClientCommand(CommandType.remove_by_id, id);
                        }
                    } catch (Exception e) {
                        System.out.println("Возникла проблема с id.");
                        //let the command be null_command
                    }
                }
                else{
                    clientCommand = new ClientCommand(CommandType.remove_by_id,
                            FieldReaders.readInt(scanner,"Введите id элемента:"));
                }

                break;

            case "clear":
                clientCommand = new ClientCommand(CommandType.clear);
                break;

            case "save":
                System.out.println("Данная команда недоступна клиенту.\n " +
                        "Операции по сохранению данных в файл осуществляются на сервере.");
                break;

            case "execute_script":
                if (isScript){
                    System.out.println("Запрещено вызывать execute_script из скрипта.");
                    System.out.println("Поправьте скрипт и повторите попытку.");
                }
                else{
                    File file = new File(command[1].trim());
                    if (file.exists()){
                        clientCommand = new ClientCommand(CommandType.execute_script, command[1]);
                    }
                }
                break;

            case "exit":
                scanner.close();
                System.out.println("Завершение работы.");
                System.exit(0);

            case "remove_first":
                clientCommand = new ClientCommand(CommandType.remove_first);
                break;

            case "add_if_max":
                Organisation newElement = this.newElement(isScript,"");
                if (newElement.equals(null)){
                    //let the command be null_command
                }
                else{
                    clientCommand = new ClientCommand(CommandType.add_if_max, newElement);
                }
                break;

            case "remove_lower":
                int employeesCount;
                if (isScript){
                    try {
                        employeesCount = Integer.parseInt(command[1]);
                        if (employeesCount < 0) {
                            System.out.println("Возникла проблема. Количество сотрудников не может быть меньше 0");
                        }
                        else {
                            clientCommand = new ClientCommand(CommandType.remove_lower, employeesCount);
                        }
                    } catch (Exception e) {
                        System.out.println("Возникла проблема с количеством сотрудников.");
                        //let the command be null_command
                    }
                }
                else{
                    System.out.println("Сравнение происходит по количеству сотрудников.");
                    clientCommand = new ClientCommand(CommandType.remove_lower,
                            FieldReaders.readInt(scanner, "Введите количество сотрудников:"));
                }

                break;

            case "remove_all_by_full_name":
                String fullName;
                if (isScript){
                    fullName = command[1];
                    if (fullName.isEmpty()) {
                        System.out.println("Возникла проблема. Полное имя не может быть пустым");
                        //let the command be null_command
                    }
                    else{
                        clientCommand = new ClientCommand(CommandType.remove_all_by_full_name, fullName);
                    }
                }
                else{
                    clientCommand = new ClientCommand(CommandType.remove_all_by_full_name,
                            FieldReaders.readString(scanner, "Введите полное имя:"));
                }

                break;

            case "filter_less_than_annual_turnover":
                Float turnover = 0F;

                if (isScript){
                    boolean dataIsNormal = true;
                    try {
                        turnover = Float.parseFloat(command[1]);
                        if (turnover < 0) {
                            System.out.println("Возникла проблема. Годовой оборот не может быть меньше 0");
                            dataIsNormal = false;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Возникла проблема с годовым оборотом.");
                        dataIsNormal = false;
                    }
                    if (dataIsNormal){
                        clientCommand = new ClientCommand(CommandType.filter_less_than_annual_turnover,
                                turnover);
                    }
                    else {
                        //let the command be null_command
                    }
                }
                else{
                    clientCommand = new ClientCommand(CommandType.filter_less_than_annual_turnover,
                            FieldReaders.readTurnover(scanner));
                }

                break;

            case "print_field_descending_annual_turnover":
                clientCommand = new ClientCommand(CommandType.print_field_descending_annual_turnover);
                break;

            default:
                System.out.println("Введённая команда не опознана.");
                System.out.println("Попробуйте ввести команду help для вывода списка команд.");
                break;
        }
        return clientCommand;
    }
}
