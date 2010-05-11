
package de.thorstenberger.examServer.ws.opal;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_02-b08-fcs
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "GroupMembersWS", targetNamespace = "groupmembers.services.webservices.olat.bps.de", wsdlLocation = "https://bildungsportal.sachsen.de/opal/services/GroupMembersWS?wsdl")
public class GroupMembersWS_Service
    extends Service
{

    private final static URL GROUPMEMBERSWS_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("https://bildungsportal.sachsen.de/opal/services/GroupMembersWS?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        GROUPMEMBERSWS_WSDL_LOCATION = url;
    }

    public GroupMembersWS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GroupMembersWS_Service() {
        super(GROUPMEMBERSWS_WSDL_LOCATION, new QName("groupmembers.services.webservices.olat.bps.de", "GroupMembersWS"));
    }

    /**
     * 
     * @return
     *     returns GroupMembersWS
     */
    @WebEndpoint(name = "GroupMembersWSSOAP")
    public GroupMembersWS getGroupMembersWSSOAP() {
        return (GroupMembersWS)super.getPort(new QName("groupmembers.services.webservices.olat.bps.de", "GroupMembersWSSOAP"), GroupMembersWS.class);
    }

}