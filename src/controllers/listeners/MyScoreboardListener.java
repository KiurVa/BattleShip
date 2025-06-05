package controllers.listeners;

import models.Database;
import models.Model;
import models.ScoreData;
import views.View;
import views.dialogs.ScoreBoardDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MyScoreboardListener implements ActionListener {
    private Model model;
    private View view;
    private JDialog dlgScoreboard;

    public MyScoreboardListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ScoreData> result;
        if (view.getRdoFile().isSelected()) {
            result = model.readFromFile();
            if (createTable(result)) {
                setupDlgScoreboard();
            } else {
                JOptionPane.showMessageDialog(view, "Andmeid pole");
            }
        } else { //andmebaas
            try (Database db = new Database(model)) {
                result = db.select(model.getBoardSize());
                if (!result.isEmpty() && createTableDb(result)) {
                    setupDlgScoreboard();
                } else {
                    JOptionPane.showMessageDialog(view, "Andmebaasi tabel on tühi");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private boolean createTableDb(ArrayList<ScoreData> result) {
        if (!result.isEmpty()) {
            String[][] data = new String[result.size()][5]; //Viie veeruga tabel
            for (int i = 0; i < result.size(); i++) {
                data[i][0] = result.get(i).getName();
                data[i][1] = result.get(i).formatGameTime(result.get(i).getTime());
                data[i][2] = String.valueOf(result.get(i).getClicks());
                data[i][3] = String.valueOf(result.get(i).getBoard());
                data[i][4] = result.get(i).getPlayedTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            }

            //Loome read-only TableModel(topelt klikk lahtris võimatu)
            DefaultTableModel tableModel = new DefaultTableModel(data, model.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Ei luba lahtri sisu muuta edetabelit vaadates
                }
            };
            JTable table = new JTable(tableModel);
            //TODO tabelil klikkimine
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && !e.isConsumed()) {
                        e.consume();
                        int row = table.rowAtPoint(e.getPoint());
                        int col = table.columnAtPoint(e.getPoint());
                        // Näita kogu rida
                        StringBuilder rowData = new StringBuilder();
                        for (int i = 0; i < table.getColumnCount(); i++) {
                            rowData.append(table.getValueAt(row, i)).append(" | ");
                        }
                        JOptionPane.showMessageDialog(table, "Valitud rida: \n" + rowData);
                        //Ainult lahtri sisu
                        Object cellObject = table.getValueAt(row, col);
                        JOptionPane.showMessageDialog(table, "Valitud lahter: \n" + cellObject);
                    }
                }
            });

            //Teeme tabeli päise rasvase
            JTableHeader header = table.getTableHeader();
            Font headerFont = header.getFont().deriveFont(Font.BOLD);
            header.setFont(headerFont);

            int[] columnWidths = {100, 120, 80, 90, 150};
            for (int i = 0; i < columnWidths.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }
            //Joondame alates teisest veerust paremale serva
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            for (int i = 1; i < model.getColumnNames().length; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            }

            dlgScoreboard = new ScoreBoardDialog(view);
            JScrollPane scrollPane = new JScrollPane(table);
            dlgScoreboard.add(scrollPane);
            dlgScoreboard.setTitle("Edetabel andmebaas");
            return true;
        }
        return false;
    }

    private boolean createTable(ArrayList<ScoreData> result) {
        if (!result.isEmpty()) {
            Collections.sort(result); //ScoreData failis see meetod
            //Loome kahe mõõtmelise stringide massiivi
            String[][] data = new String[result.size()][5]; //Viie veeruga tabel
            for (int i = 0; i < result.size(); i++) {
                data[i][0] = result.get(i).getName();
                data[i][1] = result.get(i).formatGameTime(result.get(i).getTime());
                data[i][2] = String.valueOf(result.get(i).getClicks());
                data[i][3] = String.valueOf(result.get(i).getBoard());
                data[i][4] = result.get(i).getPlayedTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            }

            JTable table = new JTable(data, model.getColumnNames()); //ColumnName on massiiv
            //Määrame veergude laiused
            int[] columnWidths = {100, 80, 60, 80, 160};
            for (int i = 0; i < columnWidths.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }

            //Loo Edetabeli aken kerimisribaga
            dlgScoreboard = new ScoreBoardDialog(view);
            dlgScoreboard.add(new JScrollPane(table));
            dlgScoreboard.setTitle("Edetabel Failist");
            return true;
        }
        return false;
    }

    private void setupDlgScoreboard() {
        dlgScoreboard.setModal(true); //Teise akna peal
        dlgScoreboard.pack();
        dlgScoreboard.setLocationRelativeTo(null); //Paigutame keset ekraani
        dlgScoreboard.setVisible(true); //Teeme nähtavaks
    }
}
