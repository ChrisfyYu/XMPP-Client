/*
 * ContactToolTip.java
 *
 * Created on 30 April 2008, 19:02
 */
package xmppclient;

import javax.swing.JToolTip;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

/**
 *
 * @author Lee Boynton (323326)
 */
public class ContactToolTip extends JToolTip
{
    private RosterEntry rosterEntry;
    private Presence presence;

    /** Creates new form ContactToolTip
     * @param JID 
     */
    public ContactToolTip(String JID)
    {
        initComponents();
    }

    /**
     * Displays a JToolTip containing information about the given user
     * @param rosterEntry The user to display information about
     * @param x The X location on screen
     * @param y The Y location on screen
     */
    public void show(RosterEntry rosterEntry, int x, int y)
    {
        this.rosterEntry = rosterEntry;
        presence = MainUI.connection.getRoster().getPresence(rosterEntry.getUser());

        nameLabel.setText(Utils.getNickname(rosterEntry));
        statusLabel.setText(Utils.getStatus(presence));
        JIDLabel.setText(rosterEntry.getUser());
        avatarLabel.setIcon(Utils.getAvatar(rosterEntry, 48));
        setVisible(true);
        this.setSize(200, 500);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        JIDLabel = new javax.swing.JLabel();
        avatarLabel = new javax.swing.JLabel();

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        nameLabel.setText("Nickname");

        statusLabel.setText("Status");

        JIDLabel.setText("JID");

        avatarLabel.setIcon(Utils.getAvatar(rosterEntry, 50));
        avatarLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(statusLabel)
                    .addComponent(JIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(avatarLabel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JIDLabel))
                    .addComponent(avatarLabel))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JIDLabel;
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
