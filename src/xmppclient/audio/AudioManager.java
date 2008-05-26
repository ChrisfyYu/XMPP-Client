/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmppclient.audio;

import xmppclient.audio.packet.Audio;
import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import xmppclient.audio.provider.AudioProvider;

/**
 *
 * @author Lee Boynton (323326)
 */
public class AudioManager
{
    private XMPPConnection connection;
    private List<AudioRequestListener> audioRequestListeners;
    private List<AudioResponseListener> audioResponseListeners;

    static
    {
        ProviderManager providerManager = ProviderManager.getInstance();
        providerManager.addIQProvider(Audio.ELEMENTNAME, Audio.NAMESPACE, new AudioProvider());

        XMPPConnection.addConnectionCreationListener(new ConnectionCreationListener()
        {
            @Override
            public void connectionCreated(XMPPConnection connection)
            {
                AudioManager.setServiceEnabled(connection, true);
            }
        });
    }

    public AudioManager(final XMPPConnection connection, final String libraryPath)
    {
        this.connection = connection;
        this.addRequestListener(new AudioRequestListener()
        {
            @Override
            public void audioRequested(AudioMessage request)
            {
                System.out.println("Audio library requested");
                AudioLibrary library = new AudioLibrary(libraryPath);
                library.generateListing();
                Audio audio = new Audio(library.getAudioFiles());
                audio.setFrom(connection.getUser());
                audio.setTo(request.getFrom());
                connection.sendPacket(audio);
            }
        });
    }

    public synchronized static void setServiceEnabled(XMPPConnection connection, boolean enabled)
    {
        if (isServiceEnabled(connection) == enabled)
        {
            return;
        }

        if (enabled)
        {
            ServiceDiscoveryManager.getInstanceFor(connection).addFeature(Audio.NAMESPACE);
        }
        else
        {
            ServiceDiscoveryManager.getInstanceFor(connection).removeFeature(Audio.NAMESPACE);
        }
    }

    public static boolean isServiceEnabled(XMPPConnection connection)
    {
        return ServiceDiscoveryManager.getInstanceFor(connection).includesFeature(
                Audio.NAMESPACE);
    }

    public void sendRequest(String JID)
    {
        Audio request = new Audio();
        request.setTo(JID);
        request.setFrom(connection.getUser());
        connection.sendPacket(request);
    }

    public void addResponseListener(AudioResponseListener responseListener)
    {
        if (responseListener != null)
        {
            if (audioResponseListeners == null)
            {
                initAudioResponseListeners();
            }
            synchronized (responseListener)
            {
                audioResponseListeners.add(responseListener);
            }
        }
    }

    public void addRequestListener(AudioRequestListener requestListener)
    {
        if (requestListener != null)
        {
            if (audioRequestListeners == null)
            {
                initAudioRequestListeners();
            }
            synchronized (requestListener)
            {
                audioRequestListeners.add(requestListener);
            }
        }
    }

    private void initAudioRequestListeners()
    {
        PacketFilter initRequestFilter = new PacketFilter()
        {
            // Return true if we accept this packet
            @Override
            public boolean accept(Packet packet)
            {
                if (packet instanceof IQ)
                {
                    IQ iq = (IQ) packet;

                    if (iq.getType().equals(IQ.Type.GET))
                    {
                        if (iq instanceof Audio)
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
        };

        audioRequestListeners = new ArrayList<AudioRequestListener>();

        // Start a packet listener for session initiation requests
        connection.addPacketListener(new PacketListener()
        {
            @Override
            public void processPacket(Packet packet)
            {
                triggerAudioRequested((Audio) packet);
            }
        }, initRequestFilter);
    }
    
    private void initAudioResponseListeners()
    {
        PacketFilter initResponseFilter = new PacketFilter()
        {
            // Return true if we accept this packet
            @Override
            public boolean accept(Packet packet)
            {
                if (packet instanceof IQ)
                {
                    IQ iq = (IQ) packet;

                    if (iq.getType().equals(IQ.Type.SET))
                    {
                        if (iq instanceof Audio)
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
        };

        audioResponseListeners = new ArrayList<AudioResponseListener>();

        // Start a packet listener for session initiation requests
        connection.addPacketListener(new PacketListener()
        {
            @Override
            public void processPacket(Packet packet)
            {
                triggerAudioResponse((Audio) packet);
            }
        }, initResponseFilter);
    }

    private void triggerAudioRequested(Audio audio)
    {
        AudioRequestListener[] audioRequestListeners = null;

        // Make a synchronized copy of the listenerJingles
        synchronized (this.audioRequestListeners)
        {
            audioRequestListeners = new AudioRequestListener[this.audioRequestListeners.size()];
            this.audioRequestListeners.toArray(audioRequestListeners);
        }

        // ... and let them know of the event
        AudioMessage request = new AudioMessage(this, audio);
        for (int i = 0; i < audioRequestListeners.length; i++)
        {
            audioRequestListeners[i].audioRequested(request);
        }
    }
    
    private void triggerAudioResponse(Audio audio)
    {
        AudioResponseListener[] audioResponseListeners = null;

        // Make a synchronized copy of the listenerJingles
        synchronized (this.audioResponseListeners)
        {
            audioResponseListeners = new AudioResponseListener[this.audioResponseListeners.size()];
            this.audioResponseListeners.toArray(audioResponseListeners);
        }

        // ... and let them know of the event
        AudioMessage response = new AudioMessage(this, audio);
        for (int i = 0; i < audioResponseListeners.length; i++)
        {
            audioResponseListeners[i].audioResponse(response);
        }
    }
}