/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.keywordsearch;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import org.sleuthkit.autopsy.coreutils.StringExtract;
import org.sleuthkit.autopsy.coreutils.StringExtract.StringExtractUnicodeTable.SCRIPT;
import org.sleuthkit.autopsy.ingest.IngestManager;

/**
 * Advanced configuration panel handling languages config.
 */
public class KeywordSearchConfigurationPanel3 extends javax.swing.JPanel {

    private static KeywordSearchConfigurationPanel3 instance = null;
    private final Logger logger = Logger.getLogger(KeywordSearchConfigurationPanel3.class.getName());
    private final Map<String, StringExtract.StringExtractUnicodeTable.SCRIPT> scripts = new HashMap<String, StringExtract.StringExtractUnicodeTable.SCRIPT>();
    private ActionListener updateLanguagesAction;

    /**
     * Creates new form KeywordSearchConfigurationPanel3
     */
    public KeywordSearchConfigurationPanel3() {
        initComponents();
        customizeComponents();
    }

    public static KeywordSearchConfigurationPanel3 getDefault() {
        if (instance == null) {
            instance = new KeywordSearchConfigurationPanel3();
        }
        return instance;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        activateWidgets();
    }

    private void customizeComponents() {


        updateLanguagesAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<SCRIPT> toUpdate = new ArrayList<SCRIPT>();
                final int components = checkPanel.getComponentCount();
                for (int i = 0; i < components; ++i) {
                    JCheckBox ch = (JCheckBox) checkPanel.getComponent(i);
                    if (ch.isSelected()) {
                        SCRIPT s = scripts.get(ch.getText());
                        toUpdate.add(s);
                    }
                }
                KeywordSearchIngestModule.getDefault().setStringExtractScripts(toUpdate);

            }
        };

        initScriptsCheckBoxes();
        reloadScriptsCheckBoxes();

    }

    private void activateScriptsCheckboxes(boolean activate) {
        final int components = checkPanel.getComponentCount();
        for (int i = 0; i < components; ++i) {
            JCheckBox ch = (JCheckBox) checkPanel.getComponent(i);
            ch.setEnabled(activate);
        }
    }

    private static String getLangText(SCRIPT script) {
        StringBuilder sb = new StringBuilder();
        sb.append(script.toString()).append(" (");
        sb.append(script.getLanguages());
        sb.append(")");
        return sb.toString();
    }
    
    private void initScriptsCheckBoxes() {
        final List<StringExtract.StringExtractUnicodeTable.SCRIPT> supportedScripts = StringExtract.getSupportedScripts();
        checkPanel.setLayout(new GridLayout(0, 1));
        for (StringExtract.StringExtractUnicodeTable.SCRIPT s : supportedScripts) {
            String text = getLangText(s);
            JCheckBox ch = new JCheckBox(text);
            ch.addActionListener(updateLanguagesAction);
            checkPanel.add(ch);
            ch.setSelected(false);
            scripts.put(text, s);
        }
    }

    private void reloadScriptsCheckBoxes() {
        final KeywordSearchIngestModule service = KeywordSearchIngestModule.getDefault();
        
        boolean utf16 = 
                Boolean.parseBoolean(service.getStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF16.toString()));
       
        enableUTF16Checkbox.setSelected(utf16);
        
        boolean utf8 = 
                Boolean.parseBoolean(service.getStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF8.toString()));
        enableUTF8Checkbox.setSelected(utf8);
        
        final List<SCRIPT> serviceScripts = service.getStringExtractScripts();
        final int components = checkPanel.getComponentCount();
        
        for (int i = 0; i < components; ++i) {
            JCheckBox ch = (JCheckBox) checkPanel.getComponent(i);
            
            StringExtract.StringExtractUnicodeTable.SCRIPT script = scripts.get(ch.getText());
            
            ch.setSelected(serviceScripts.contains(script));
        }
        
    }

    private void activateWidgets() {
        reloadScriptsCheckBoxes();
        
        final KeywordSearchIngestModule service = KeywordSearchIngestModule.getDefault();
        
         boolean utf16 = 
                Boolean.parseBoolean(service.getStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF16.toString()));
       
        enableUTF16Checkbox.setSelected(utf16);
        
        boolean utf8 = 
                Boolean.parseBoolean(service.getStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF8.toString()));
        enableUTF8Checkbox.setSelected(utf8);
        final boolean extractEnabled = utf16 || utf8;
        
        boolean ingestNotRunning = !IngestManager.getDefault().isIngestRunning()
        && ! IngestManager.getDefault().isModuleRunning(KeywordSearchIngestModule.getDefault());;
        //enable / disable checboxes
        activateScriptsCheckboxes(extractEnabled && ingestNotRunning);
        enableUTF16Checkbox.setEnabled(ingestNotRunning);
        enableUTF8Checkbox.setEnabled(ingestNotRunning);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        languagesLabel = new javax.swing.JLabel();
        langPanel = new javax.swing.JScrollPane();
        checkPanel = new javax.swing.JPanel();
        enableUTF8Checkbox = new javax.swing.JCheckBox();
        enableUTF16Checkbox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(languagesLabel, org.openide.util.NbBundle.getMessage(KeywordSearchConfigurationPanel3.class, "KeywordSearchConfigurationPanel3.languagesLabel.text")); // NOI18N

        langPanel.setPreferredSize(new java.awt.Dimension(430, 361));

        checkPanel.setPreferredSize(new java.awt.Dimension(400, 361));

        javax.swing.GroupLayout checkPanelLayout = new javax.swing.GroupLayout(checkPanel);
        checkPanel.setLayout(checkPanelLayout);
        checkPanelLayout.setHorizontalGroup(
            checkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
        checkPanelLayout.setVerticalGroup(
            checkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        langPanel.setViewportView(checkPanel);

        org.openide.awt.Mnemonics.setLocalizedText(enableUTF8Checkbox, org.openide.util.NbBundle.getMessage(KeywordSearchConfigurationPanel3.class, "KeywordSearchConfigurationPanel3.enableUTF8Checkbox.text")); // NOI18N
        enableUTF8Checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableUTF8CheckboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(enableUTF16Checkbox, org.openide.util.NbBundle.getMessage(KeywordSearchConfigurationPanel3.class, "KeywordSearchConfigurationPanel3.enableUTF16Checkbox.text")); // NOI18N
        enableUTF16Checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableUTF16CheckboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(KeywordSearchConfigurationPanel3.class, "KeywordSearchConfigurationPanel3.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(langPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(enableUTF16Checkbox)
                            .addComponent(languagesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(enableUTF8Checkbox)))
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enableUTF16Checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableUTF8Checkbox)
                .addGap(4, 4, 4)
                .addComponent(languagesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(langPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void enableUTF8CheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableUTF8CheckboxActionPerformed
        final KeywordSearchIngestModule service = KeywordSearchIngestModule.getDefault();
        
        boolean selected = this.enableUTF8Checkbox.isSelected();
        
        service.setStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF8.toString(),
                Boolean.toString(selected));
        
        activateScriptsCheckboxes(selected || this.enableUTF16Checkbox.isSelected());
        
    }//GEN-LAST:event_enableUTF8CheckboxActionPerformed

    private void enableUTF16CheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableUTF16CheckboxActionPerformed
        final KeywordSearchIngestModule service = KeywordSearchIngestModule.getDefault();
        
        boolean selected = this.enableUTF16Checkbox.isSelected();
        
        service.setStringExtractOption(AbstractFileExtract.ExtractOptions.EXTRACT_UTF16.toString(),
                Boolean.toString(selected));
        
        activateScriptsCheckboxes(selected || this.enableUTF8Checkbox.isSelected());
    }//GEN-LAST:event_enableUTF16CheckboxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel checkPanel;
    private javax.swing.JCheckBox enableUTF16Checkbox;
    private javax.swing.JCheckBox enableUTF8Checkbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane langPanel;
    private javax.swing.JLabel languagesLabel;
    // End of variables declaration//GEN-END:variables
}
