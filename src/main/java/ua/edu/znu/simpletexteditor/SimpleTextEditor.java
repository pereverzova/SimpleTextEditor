package ua.edu.znu.simpletexteditor;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Клас SimpleTextEditor, який представляє собою простий текстовий редактор з графічним інтерфейсом користувача.
 *
 * @author evape
 */
class SimpleTextEditor {
    // Мітка відображення повідомлень
    private final JLabel messageLabel;

    // Область редагування тексту
    private final JTextArea textArea;

    // Текстове поле введення назви файлу
    private final JTextField fileNameTextField;
    // Текстове поле для введення рядка для пошуку
    private final JTextField searchTextField;

    // Індекс зберігання поточного положення курсора при пошуку
    private int searchIndex;

    /**
     * Конструктор класу SimpleTextEditor.
     */
    SimpleTextEditor() {

        JFrame frame = new JFrame("A Simple Text Editor");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(270, 420);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageLabel = new JLabel();
        messageLabel.setPreferredSize(new Dimension(200, 30));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel separatorLabel = new JLabel();
        separatorLabel.setPreferredSize(new Dimension(200, 30));

        JLabel findLabel = new JLabel("Search For:");
        findLabel.setPreferredSize(new Dimension(70, 20));
        findLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel fileNameLabel = new JLabel("Filename:");
        fileNameLabel.setPreferredSize(new Dimension(70, 20));
        fileNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(250, 200));

        fileNameTextField = new JTextField(15);

        textArea.addCaretListener(e -> {
            String text = textArea.getText();
            messageLabel.setText("Current size: " + text.length());
            searchIndex = textArea.getCaretPosition();
        });


        JButton saveButton = new JButton("Save File");
        JButton loadButton = new JButton("Load File");

        saveButton.addActionListener(e -> save());

        loadButton.addActionListener(e -> load());

        searchTextField = new JTextField(15);

        JButton findButton = new JButton("Find From Top");
        JButton findNextButton = new JButton("Find Next");

        findButton.addActionListener(e -> {
            searchIndex = 0;
            search(searchIndex);
        });


        findNextButton.addActionListener(e -> search(searchIndex + 1));


        Container contentPane = frame.getContentPane();
        contentPane.add(scrollPane);
        contentPane.add(findLabel);
        contentPane.add(searchTextField);
        contentPane.add(findButton);
        contentPane.add(findNextButton);
        contentPane.add(separatorLabel);
        contentPane.add(fileNameLabel);
        contentPane.add(fileNameTextField);
        contentPane.add(saveButton);
        contentPane.add(loadButton);
        contentPane.add(messageLabel);

        frame.setVisible(true);
    }

    /**
     * Метод збереження тексту в файл.
     */
    private void save() {
        FileWriter fileWriter;
        String fileName = fileNameTextField.getText();

        if (fileName.isEmpty()) {
            messageLabel.setText("No filename present.");
            return;
        }

        try {
            fileWriter = new FileWriter(fileName);
            textArea.write(fileWriter);
            fileWriter.close();
        } catch (IOException exc) {
            messageLabel.setText("Error opening or writing file.");
            return;
        }

        messageLabel.setText("File written sucessfully.");
    }

    /**
     * Метод завантаження тексту з файлу.
     */
    private void load() {
        FileReader fileReader;

        String fileName = fileNameTextField.getText();

        if (fileName.isEmpty()) {
            messageLabel.setText("No filename present.");
            return;
        }

        try {
            fileReader = new FileReader(fileName);
            textArea.read(fileReader, null);
            fileReader.close();
        } catch (IOException exc) {
            messageLabel.setText("Error opening or reading file.");
            return;
        }

        searchIndex = 0;

        messageLabel.setText("File loaded successfully.");
    }

    /**
     * Метод пошуку пошукового тексту в тексті файлу, починаючи з певної позиції.
     *
     * @param start - початковий індекс для пошуку
     */
    private void search(final int start) {
        String text = textArea.getText();
        String searchText = searchTextField.getText();

        int idx = text.indexOf(searchText, start);

        if (idx > -1) {
            textArea.setCaretPosition(idx);
            searchIndex = idx;
            messageLabel.setText("String found.");
        } else {
            messageLabel.setText("String not found.");
        }

        textArea.requestFocusInWindow();
    }

    /**
     * Головний метод створення екземпляра класу SimpleTextEditor шляхом запуску програми в графічному режимі.
     *
     * @param args – аргументи командного рядка
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(SimpleTextEditor::new);
    }
}
