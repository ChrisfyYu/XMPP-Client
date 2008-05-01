/*
 * ChatPanel.java
 *
 * Created on 14 April 2008, 23:30
 */

package xmppclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.packet.VCard;

/**
 *
 * @author  Lee Boynton (323326)
 */
public class ChatPanel extends javax.swing.JPanel 
{
    private Chat chat;
    private JFrame frame;
    
    public ChatPanel(Chat chat, JFrame frame)
    {
        this.frame = frame;
        this.chat = chat;
        initComponents();
    }
    
    public Chat getChat()
    {
        return chat;
    }
    
    @Override
    public String getName()
    {
        VCard vCard = new VCard();
        
        if(getRosterEntry() != null && getRosterEntry().getName() != null && !getRosterEntry().getName().equals(""))
            return getRosterEntry().getName();
        
        try
        {
            vCard.load(XMPPClientUI.connection, chat.getParticipant());
            if(vCard.getNickName() != null) return vCard.getNickName();
        }
        catch (XMPPException ex) {}
        
        return chat.getParticipant();
    }

    public void addMessage(Message message)
    {
        messageTextArea.append(getName() + ": " + message.getBody() + "\n");
    }
    
    /**
     * Gets the roster entry of the chat participant from the contact list.
     * @return The roster entry, or null if not in roster
     */
    private RosterEntry getRosterEntry()
    {
        /*
         * NOTE:
         * chat.getParticipant() returns the JID with the resource
         * The roster JID does not have the resource
         */
        return XMPPClientUI.connection.getRoster().getEntry(chat.getParticipant());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contact = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        sendFileButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();

        contact.setFont(new java.awt.Font("Tahoma", 1, 12));
        contact.setText(chat.getParticipant());

        sendTextArea.setColumns(20);
        sendTextArea.setFont(new java.awt.Font("Tahoma", 0, 10));
        sendTextArea.setLineWrap(true);
        sendTextArea.setRows(1);
        sendTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sendTextAreaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sendTextAreaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(sendTextArea);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("To:");

        messageTextArea.setColumns(20);
        messageTextArea.setEditable(false);
        messageTextArea.setFont(new java.awt.Font("Tahoma", 0, 10));
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(5);
        messageTextArea.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(messageTextArea);

        sendFileButton.setText("Send File");
        sendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileButtonActionPerformed(evt);
            }
        });

        statusLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
        statusLabel.setText("(" + Utils.getStatus(XMPPClientUI.connection.getRoster().getPresence(chat.getParticipant())) + ")");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contact)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(sendFileButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendFileButton)
                    .addComponent(jLabel1)
                    .addComponent(contact)
                    .addComponent(statusLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane2, sendButton});

    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
    send();
}//GEN-LAST:event_sendButtonActionPerformed
    private void send()
    {
        if(sendTextArea.getText().trim().equals("")) return;
        
        try
        {
            chat.sendMessage(sendTextArea.getText().trim());
            messageTextArea.append("Me: " + sendTextArea.getText() + "\n");
            sendTextArea.setText("");
        }
        catch (XMPPException ex)
        {
            Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendTextAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendTextAreaKeyPressed
       if(evt.getKeyCode() == 10) send();
    }//GEN-LAST:event_sendTextAreaKeyPressed

    private void sendTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendTextAreaKeyReleased
        if(evt.getKeyCode() == 10) sendTextArea.setText("");
    }//GEN-LAST:event_sendTextAreaKeyReleased

private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileButtonActionPerformed

    final JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAccessory(new FileTransferChooserAccessory());
    fileChooser.addPropertyChangeListener(new PropertyChangeListener() 
    {
        public void propertyChange(PropertyChangeEvent evt)
        {
            if(evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY))
            {
                JFileChooser chooser = (JFileChooser)evt.getSource();
                try
                {
                    ((FileTransferChooserAccessory)fileChooser.getAccessory()).setFilename(chooser.getSelectedFile().getName());  
                }
                catch(Exception e) {}
            }
        }
    });
    
    int selection = fileChooser.showDialog(frame, "Send file to " + getName());
    
    if(selection == JFileChooser.APPROVE_OPTION)
    {
        FileTransferManager manager = new FileTransferManager(XMPPClientUI.connection);
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(XMPPClientUI.connection.getRoster().getPresence(getRosterEntry().getUser()).getFrom());
        
        try
        {
            transfer.sendFile(fileChooser.getSelectedFile(), ((FileTransferChooserAccessory)fileChooser.getAccessory()).getFileDescription());
            new FileTransferUI(transfer);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(FileTransferChooser.class.getName()).log(Level.SEVERE, null, ex);
        }        
        catch (XMPPException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(FileTransferChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_sendFileButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contact;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton sendFileButton;
    private javax.swing.JTextArea sendTextArea;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
