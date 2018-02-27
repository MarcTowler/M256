package schoolgui;

import java.util.*;                 
import java.awt.event.*;            
import java.text.*;                 
import m256date.*;                  
import schoolcore.*;                /**the core system*/

/**
 * Creates a GUI for the School System, and allows user interaction with the 
 * core system, via the coordinating object.
 *
 * v2 21/1/13 sets minimumSize of components following
 * suggestions made in M256 Software Queries forum 2012.
 */

public class SchoolGUI extends javax.swing.JFrame 
{
    private SchoolCoord school;         /**the coordinating object*/
    private Map<String, Form> formMap; /**the forms*/
    
    /** Creates new SchoolGUI */
    public SchoolGUI() 
    {
        //get the coordinating object
        school = SchoolCoord.getSchoolCoordObject();
        //construct a map of the school's forms, keyed by name
        formMap = new TreeMap<String, Form>(); 
        for (Form f: school.getForms()) 
        {
            formMap.put(f.getName(), f);
        }
        //set up the GUI components
        initComponents();
        //select the first form
        formNamesList.setSelectedIndex(0);
        pupilsList.setPrototypeCellValue("Pupil Name and date of Birth");
        displayTeacherWithMostPupils();
        //set the window closing action
        addWindowListener(new CloseQuit());
    }
    
    /**
     * Displays  details of the selected form:
     * teacher's name and pupil names.
     */
    private void displayFormDetails()
    {   
        //get the selected form
        Form theForm = formMap.get(formNamesList.getSelectedValue());
        //display the information
        formTextArea.setText(theForm.getName());
        teacherField.setText(school.getTeacher(theForm).getName());        
        spacesField.setText(Integer.toString(SchoolCoord.MAX_FORM_SIZE - theForm.getSize()));        
        displayOldestPupil();
        pupilsList.setListData(new Vector<Pupil>(school.getPupils(theForm)));
        
        // if form is full disable the enrolPanel to prevent addition of more pupils
        if (SchoolCoord.MAX_FORM_SIZE == theForm.getSize())
        {
            enablePanel(false, enrolPanel);
        } 
        else
        {
            enablePanel(true, enrolPanel);
        }
    }
    
    /**
     * Records the enrolment of a new pupil
     */
    private void doEnrolment()
    {
        //get the selected form
        Form theForm = formMap.get(formNamesList.getSelectedValue());
        String messageText = "";
        String messageTitle = "Warning";
        int messageIcon = javax.swing.JOptionPane.ERROR_MESSAGE;
        //check the form size 
        if ( nameField.getText().equals("") | birthDateField.getText().equals(""))
        {
            messageText = "Enter a name and a date of birth";
        }
        else
        {            
            int age = 0;
            try
            { 
                //get the date
                M256Date date = new M256Date(birthDateField.getText());
                //check the age of the pupil                    
                age = date.getAge();
                //call on the coordinating object to enrol the pupil
                school.enrolPupil(nameField.getText(), date, theForm);
                messageText = "Pupil enrolled (age " + age +")";
                messageTitle = "Information";
                messageIcon = javax.swing.JOptionPane.INFORMATION_MESSAGE;
                displayFormDetails();
                nameField.setText("");
                birthDateField.setText("Age must be between 4 and 18");
                // New pupil may have changed this...
                displayTeacherWithMostPupils();
            }  
            catch (IllegalArgumentException iae)
            {
                if (age < 4) 
                {
                    messageText = "Pupil too young! (age " + age +")";
                }
                else if (age > 18) 
                {
                    messageText = "Pupil too old! (age " + age +")";
                }
            }
            catch (IllegalStateException ise)
            {
                messageText = "Form is full";
            }
            catch (ParseException ex)
            { 
                messageText = "Invalid date";
            }
        }      
        messageField.setText(messageText);
        javax.swing.JOptionPane.showMessageDialog(this, messageText, messageTitle, messageIcon);
    } 
    
    /**
     * Displays the name of the teacher with the most pupils.
     */
    private void displayTeacherWithMostPupils()   
    {
        //call on the coordinating object to get the teacher
        Teacher theTeacher = school.getTeacherWithMostPupils();
        //display the teacher's name
        if (theTeacher != null)
        {
            teacherWithMostPupilsField.setText(theTeacher.getName());
        }
        else
        {
            teacherWithMostPupilsField.setText("All forms are empty");
        }
    }
    
    /**
     * Displays the name of the oldest pupil in the selected form
     */
    private void displayOldestPupil() 
    {
        //get the selected form
        Form theForm = formMap.get((String)formNamesList.getSelectedValue());
        //call on the coordinating object to get the pupil
        Pupil oldestPupil  = school.getOldestPupil(theForm);
        //display the pupil's name
        if (oldestPupil == null)
        {    
            oldestPupilField.setText("No pupils in this form");
        }
        else
        {
            oldestPupilField.setText(oldestPupil.getName());
        }
    }

    /**
     * Resets the school to its initial state
     */
    private void doReset()
    {
        if (javax.swing.JOptionPane.showConfirmDialog(this, "Do you want to remove all pupils from the school?", "Remove all Pupils?", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE) == javax.swing.JOptionPane.OK_OPTION)
        {
            school = SchoolCoord.resetSchoolCoordObject();
            formMap = new TreeMap<String, Form>(); 
            for (Form f: school.getForms()) 
            {
                formMap.put(f.getName(), f);
            }
            displayFormDetails();
            nameField.setText("");
            birthDateField.setText("Age must be between 4 and 18");
            displayTeacherWithMostPupils();
            clear();
        }
    }
    
    /**
     * A helper method to clear fields.
     */
    private void clear()
    { 
        oldestPupilField.setText("");
        messageField.setText("");
    }
    
    /**
     * Enables or disables a panel according to the value of state. All the 
     * components on the panel are also enabled/disabled.
     *
     * @param   state   if true the panel is enabled, if false it is disabled.
     * @param   aPanel  the panel to be enabled/disabled
     */
    private void enablePanel(boolean state, javax.swing.JPanel aPanel)
    {
        for (int i = 0; i < aPanel.getComponentCount(); i++)
        {
           aPanel.getComponent(i).setEnabled(state);
        }
        if (state)
        {
            aPanel.setBackground(new java.awt.Color(204,204,255));
            ((javax.swing.border.TitledBorder)aPanel.getBorder()).setTitleColor(((javax.swing.border.TitledBorder)mainFormPanel.getBorder()).getTitleColor());
        }
        else
        {
            aPanel.setBackground(topPanel.getBackground());
            ((javax.swing.border.TitledBorder)aPanel.getBorder()).setTitleColor(java.awt.Color.GRAY);
        }
        aPanel.setEnabled(state);
    }
      
    /**
     * Saves the state of the coordinating instance 
     * on closing the application/
     */
    private class CloseQuit extends WindowAdapter 
    {
        public void windowClosing(WindowEvent e) 
        {
            school.save();
        }
      }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(
            new Runnable() 
            {
                public void run() {
                    new SchoolGUI().setVisible(true);
                }
            }
        );
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        schoolScrollPane = new javax.swing.JScrollPane();
        schoolPanel = new javax.swing.JPanel();
        mainFormPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        formLabel = new javax.swing.JLabel();
        formTextArea = new javax.swing.JTextField();
        teacherLabel = new javax.swing.JLabel();
        teacherField = new javax.swing.JTextField();
        oldestPupilLabel = new javax.swing.JLabel();
        oldestPupilField = new javax.swing.JTextField();
        pupilLabel = new javax.swing.JLabel();
        spacesLabel = new javax.swing.JLabel();
        spacesField = new javax.swing.JTextField();
        pupilsPane = new javax.swing.JScrollPane();
        pupilsList = new javax.swing.JList();
        enrolPanel = new javax.swing.JPanel();
        instructionsLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        birthDateLabel = new javax.swing.JLabel();
        birthDateField = new javax.swing.JTextField();
        enrolButton = new javax.swing.JButton();
        outcomePanel = new javax.swing.JPanel();
        messageField = new javax.swing.JTextField();
        resetPanel = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        leftPanel = new javax.swing.JPanel();
        selectFormPanel = new javax.swing.JPanel();
        formNamesPanel = new javax.swing.JScrollPane();
        formNamesList = new javax.swing.JList(formMap.keySet().toArray());
        mostPupilsPanel = new javax.swing.JPanel();
        teacherWithMostPupilsField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("School");
        setBackground(new java.awt.Color(153, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        schoolScrollPane.setMinimumSize(new java.awt.Dimension(720, 455));

        schoolPanel.setLayout(new java.awt.BorderLayout());

        mainFormPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Details"));
        mainFormPanel.setMinimumSize(new java.awt.Dimension(600, 400));
        mainFormPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        mainFormPanel.setLayout(new java.awt.BorderLayout());

        topPanel.setAlignmentX(0.0F);
        topPanel.setAlignmentY(0.0F);
        topPanel.setLayout(new java.awt.GridBagLayout());

        formLabel.setText("Form");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(formLabel, gridBagConstraints);

        formTextArea.setColumns(15);
        formTextArea.setEditable(false);
        formTextArea.setMinimumSize(new java.awt.Dimension(194, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(formTextArea, gridBagConstraints);

        teacherLabel.setText("Teacher");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(teacherLabel, gridBagConstraints);

        teacherField.setColumns(15);
        teacherField.setEditable(false);
        teacherField.setToolTipText("Teacher");
        teacherField.setMinimumSize(new java.awt.Dimension(194, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(teacherField, gridBagConstraints);

        oldestPupilLabel.setText("Oldest pupil");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(oldestPupilLabel, gridBagConstraints);

        oldestPupilField.setColumns(15);
        oldestPupilField.setEditable(false);
        oldestPupilField.setMinimumSize(new java.awt.Dimension(194, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(oldestPupilField, gridBagConstraints);

        pupilLabel.setText("Pupils");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(pupilLabel, gridBagConstraints);

        spacesLabel.setText("Spaces free");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(spacesLabel, gridBagConstraints);

        spacesField.setColumns(2);
        spacesField.setEditable(false);
        spacesField.setMinimumSize(new java.awt.Dimension(194, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(spacesField, gridBagConstraints);

        pupilsPane.setMinimumSize(new java.awt.Dimension(159, 115));

        pupilsList.setMinimumSize(new java.awt.Dimension(150, 100));
        pupilsList.setVisibleRowCount(6);
        pupilsPane.setViewportView(pupilsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(pupilsPane, gridBagConstraints);

        mainFormPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        enrolPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Enrol Pupil"));
        enrolPanel.setLayout(new java.awt.GridBagLayout());

        instructionsLabel.setText("Select a form with free spaces and enter pupil details below");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        enrolPanel.add(instructionsLabel, gridBagConstraints);

        nameLabel.setDisplayedMnemonic('N');
        nameLabel.setLabelFor(nameField);
        nameLabel.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        enrolPanel.add(nameLabel, gridBagConstraints);

        nameField.setColumns(16);
        nameField.setToolTipText("Name of student");
        nameField.setMinimumSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        enrolPanel.add(nameField, gridBagConstraints);

        birthDateLabel.setDisplayedMnemonic('D');
        birthDateLabel.setLabelFor(birthDateField);
        birthDateLabel.setText("Date of birth (dd/mm/yy)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        enrolPanel.add(birthDateLabel, gridBagConstraints);

        birthDateField.setColumns(16);
        birthDateField.setText("Age must be between 4 and 18");
        birthDateField.setToolTipText("Age must be between 4 and 18");
        birthDateField.setMinimumSize(new java.awt.Dimension(150, 20));
        birthDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dOfBFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dOfBFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        enrolPanel.add(birthDateField, gridBagConstraints);

        enrolButton.setMnemonic('E');
        enrolButton.setText("Enrol");
        enrolButton.setToolTipText("Enrol pupil");
        enrolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrolButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        enrolPanel.add(enrolButton, gridBagConstraints);

        outcomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Outcome"));
        outcomePanel.setAlignmentX(0.0F);
        outcomePanel.setAlignmentY(0.0F);
        outcomePanel.setOpaque(false);
        outcomePanel.setLayout(new java.awt.BorderLayout());

        messageField.setEditable(false);
        messageField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        messageField.setOpaque(false);
        outcomePanel.add(messageField, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        enrolPanel.add(outcomePanel, gridBagConstraints);

        mainFormPanel.add(enrolPanel, java.awt.BorderLayout.CENTER);

        schoolPanel.add(mainFormPanel, java.awt.BorderLayout.CENTER);

        resetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        resetButton.setMnemonic('R');
        resetButton.setText("Reset school");
        resetButton.setToolTipText("Resets the school to contain no pupils");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });
        resetPanel.add(resetButton);

        schoolPanel.add(resetPanel, java.awt.BorderLayout.SOUTH);

        leftPanel.setLayout(new java.awt.BorderLayout());

        selectFormPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Form"));
        selectFormPanel.setAlignmentX(0.0F);
        selectFormPanel.setAlignmentY(0.0F);
        selectFormPanel.setAutoscrolls(true);
        selectFormPanel.setLayout(new java.awt.GridBagLayout());

        formNamesList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        formNamesList.setForeground(new java.awt.Color(0, 102, 255));
        formNamesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        formNamesList.setToolTipText("Select form name from this list");
        formNamesList.setAutoscrolls(false);
        formNamesList.setFixedCellWidth(150);
        formNamesList.setOpaque(false);
        formNamesList.setSelectedIndex(1);
        formNamesList.setVisibleRowCount(6);
        formNamesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                formNamesListValueChanged(evt);
            }
        });
        formNamesPanel.setViewportView(formNamesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        selectFormPanel.add(formNamesPanel, gridBagConstraints);

        leftPanel.add(selectFormPanel, java.awt.BorderLayout.CENTER);

        mostPupilsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Teacher With Most Pupils"));
        mostPupilsPanel.setLayout(new java.awt.BorderLayout());

        teacherWithMostPupilsField.setColumns(15);
        teacherWithMostPupilsField.setEditable(false);
        teacherWithMostPupilsField.setToolTipText("Name of teacher with most pupils displayed here");
        teacherWithMostPupilsField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        teacherWithMostPupilsField.setMinimumSize(new java.awt.Dimension(150, 20));
        teacherWithMostPupilsField.setOpaque(false);
        mostPupilsPanel.add(teacherWithMostPupilsField, java.awt.BorderLayout.CENTER);
        teacherWithMostPupilsField.getAccessibleContext().setAccessibleName("Name of teacher with most pupils");

        leftPanel.add(mostPupilsPanel, java.awt.BorderLayout.SOUTH);

        schoolPanel.add(leftPanel, java.awt.BorderLayout.WEST);

        schoolScrollPane.setViewportView(schoolPanel);

        getContentPane().add(schoolScrollPane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dOfBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dOfBFocusLost
        birthDateField.setSelectionStart(0);
        birthDateField.setSelectionEnd(0);
    }//GEN-LAST:event_dOfBFocusLost

    private void dOfBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dOfBFocusGained
        birthDateField.setSelectionStart(0);
        birthDateField.setSelectionEnd(birthDateField.getText().length());
    }//GEN-LAST:event_dOfBFocusGained

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        doReset();
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void EnrolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrolButtonActionPerformed
        doEnrolment();
    }//GEN-LAST:event_EnrolButtonActionPerformed
    
    //Handles selecting  a form from the list
    private void formNamesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_formNamesListValueChanged
     if (!evt.getValueIsAdjusting()) 
     {
        clear();
        displayFormDetails();
     }
    }//GEN-LAST:event_formNamesListValueChanged

    //Handles  pressing the 'Get name of teacher with most pupils' button
    //Handles  pressing the 'Get name of oldest pupil in selected form' button
    //Handles  pressing the 'Record enrolment' button    
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField birthDateField;
    private javax.swing.JLabel birthDateLabel;
    private javax.swing.JButton enrolButton;
    private javax.swing.JPanel enrolPanel;
    private javax.swing.JLabel formLabel;
    private javax.swing.JList formNamesList;
    private javax.swing.JScrollPane formNamesPanel;
    private javax.swing.JTextField formTextArea;
    private javax.swing.JLabel instructionsLabel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainFormPanel;
    private javax.swing.JTextField messageField;
    private javax.swing.JPanel mostPupilsPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField oldestPupilField;
    private javax.swing.JLabel oldestPupilLabel;
    private javax.swing.JPanel outcomePanel;
    private javax.swing.JLabel pupilLabel;
    private javax.swing.JList pupilsList;
    private javax.swing.JScrollPane pupilsPane;
    private javax.swing.JButton resetButton;
    private javax.swing.JPanel resetPanel;
    private javax.swing.JPanel schoolPanel;
    private javax.swing.JScrollPane schoolScrollPane;
    private javax.swing.JPanel selectFormPanel;
    private javax.swing.JTextField spacesField;
    private javax.swing.JLabel spacesLabel;
    private javax.swing.JTextField teacherField;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JTextField teacherWithMostPupilsField;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
    
}
