/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiera.wsclient;



import financiera.ws.CotizacionWS;
import financiera.ws.CotizacionWS_Service;
import financiera.ws.RtaCotizacionDTO;
import java.net.URL;

/**
 *
 * @author julian
 */
public class WSCotizacion {
    
    private CotizacionWS_Service cotizacionWS_Service;
    private CotizacionWS cotizacionWS;
    private boolean inicializaWS;

    public WSCotizacion() {
        try {
            URL url = new URL("http://localhost:8083/cotizacionfinanciera/CotizacionWS?wsdl");
            inicializaWS = false;
            cotizacionWS_Service = new CotizacionWS_Service(url);
            if(cotizacionWS_Service != null) {
                this.cotizacionWS = cotizacionWS_Service.getCotizacionWSPort();
                 System.out.println("Create Web Service... Creado");
                inicializaWS = true;
            }
        } catch (Exception e) {
            System.out.println("Financiera: ERROR al intentar inicializar el WebService de la Cotización.");
            e.printStackTrace();
        }
    }

    public boolean isInicializaWS() {
        return inicializaWS;
    }

    public void setInicializaWS(boolean inicializaWS) {
        this.inicializaWS = inicializaWS;
    }

    public RtaCotizacionDTO consultarCotizacion(double montoSolicitado) {
        try {
            return this.cotizacionWS.generarCotizacion(montoSolicitado);
        } catch (Exception e) {
            System.out.println("Financiera: ERROR al intentar generar cotización.");
            e.printStackTrace();
            return null;
        }
    }

}
