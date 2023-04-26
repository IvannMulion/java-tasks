package ru.mail.polis.homework.exception;

import java.util.Map;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteControl {

    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(Map<Integer, Robot> robots) {
        connectionManager = new RobotRemoteConnectionManager(robots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        int i = 0;
        while (true) {
            try (RobotConnection robotConnection = connectionManager.getConnection(robotId)) {
                robotConnection.moveRobotTo(toX, toY);
                return;
            } catch (RobotConnectionException exception) {
                if (i == 2) {
                    throw exception;
                }
            }
            ++i;
        }
    }
}
