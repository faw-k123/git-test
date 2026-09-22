import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 * DateTimePickerDialog - 日期时间选择对话框
 * 
 * 一个简单的 Swing 对话框，用于让用户选择日期和时间。
 */
public class DateTimePickerDialog extends JDialog {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private JSpinner yearSpinner;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;
    private JLabel resultLabel;

    private LocalDateTime selectedDateTime;

    public DateTimePickerDialog(Frame parent) {
        super(parent, "选择日期和时间", true);

        LocalDateTime now = LocalDateTime.now();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.day = now.getDayOfMonth();
        this.hour = now.getHour();
        this.minute = now.getMinute();
        this.second = now.getSecond();

        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // 顶部：日期时间选择面板
        JPanel pickerPanel = new JPanel(new GridLayout(2, 6, 5, 5));

        yearSpinner   = new JSpinner(new SpinnerNumberModel(year, 1900, 2100, 1));
        monthSpinner  = new JSpinner(new SpinnerNumberModel(month, 1, 12, 1));
        daySpinner    = new JSpinner(new SpinnerNumberModel(day, 1, 31, 1));
        hourSpinner   = new JSpinner(new SpinnerNumberModel(hour, 0, 23, 1));
        minuteSpinner = new JSpinner(new SpinnerNumberModel(minute, 0, 59, 1));
        secondSpinner = new JSpinner(new SpinnerNumberModel(second, 0, 59, 1));

        pickerPanel.add(createLabel("年"));
        pickerPanel.add(yearSpinner);
        pickerPanel.add(createLabel("月"));
        pickerPanel.add(monthSpinner);
        pickerPanel.add(createLabel("日"));
        pickerPanel.add(daySpinner);
        pickerPanel.add(createLabel("时"));
        pickerPanel.add(hourSpinner);
        pickerPanel.add(createLabel("分"));
        pickerPanel.add(minuteSpinner);
        pickerPanel.add(createLabel("秒"));
        pickerPanel.add(secondSpinner);

        add(pickerPanel, BorderLayout.CENTER);

        // 底部：结果显示 + 按钮
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        resultLabel = new JLabel("已选择: " + formatDateTime(getDateTimeFromSpinners()));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(resultLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            selectedDateTime = getDateTimeFromSpinners();
            resultLabel.setText("已选择: " + formatDateTime(selectedDateTime));
            dispose();
        });

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        return label;
    }

    private LocalDateTime getDateTimeFromSpinners() {
        int y = (Integer) yearSpinner.getValue();
        int m = (Integer) monthSpinner.getValue();
        int d = (Integer) daySpinner.getValue();
        int h = (Integer) hourSpinner.getValue();
        int min = (Integer) minuteSpinner.getValue();
        int s = (Integer) secondSpinner.getValue();
        return LocalDateTime.of(y, m, d, h, min, s);
    }

    private String formatDateTime(LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime getSelectedDateTime() {
        return selectedDateTime;
    }

    // 测试入口
    public static void main(String[] args) {
        JFrame frame = new JFrame("DateTimePickerDialog 测试");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        JButton openButton = new JButton("打开日期时间选择器");
        openButton.addActionListener(e -> {
            DateTimePickerDialog dialog = new DateTimePickerDialog(frame);
            dialog.setVisible(true);
            LocalDateTime result = dialog.getSelectedDateTime();
            if (result != null) {
                JOptionPane.showMessageDialog(frame,
                        "您选择的日期时间: " + result.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(openButton);
        frame.setVisible(true);
    }
}
