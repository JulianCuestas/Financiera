/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiera.bean;


import financiera.ws.RtaCotizacionDTO;
import financiera.wsclient.WSCotizacion;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named
@ViewScoped
public class CotizacionBean implements Serializable{
    private double montoSolicitado;
    private String codigoRespuesta;
    private String mensajeRespuesta;
    private String nombreSocio;
    private String pagoTotalCredito;
    private String tasaInteres;
    private String valorCuotaMensual;

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }
    
    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getPagoTotalCredito() {
        return pagoTotalCredito;
    }

    public void setPagoTotalCredito(String pagoTotalCredito) {
        this.pagoTotalCredito = pagoTotalCredito;
    }

    public String getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(String tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public String getValorCuotaMensual() {
        return valorCuotaMensual;
    }

    public void setValorCuotaMensual(String valorCuotaMensual) {
        this.valorCuotaMensual = valorCuotaMensual;
    }
    
    public void generarCotizacion(){
        if(this.montoSolicitado > 0){
            System.out.println("MONTO ES MAYOR DE 0");
            WSCotizacion wsCotizacion = new WSCotizacion();
            if(wsCotizacion.isInicializaWS()){
                RtaCotizacionDTO rtaCotizacionDTO = wsCotizacion.consultarCotizacion(this.montoSolicitado);
                if(rtaCotizacionDTO != null){
                    this.codigoRespuesta = rtaCotizacionDTO.getCodigoRespuesta();
                    if (this.codigoRespuesta.equals("000")) {
                        this.nombreSocio = rtaCotizacionDTO.getNombreSocio();
                        this.pagoTotalCredito = rtaCotizacionDTO.getPagoTotalCredito();
                        this.valorCuotaMensual = rtaCotizacionDTO.getValorCuotaMensual();
                        this.tasaInteres = rtaCotizacionDTO.getTasaInteres();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"INFO!", rtaCotizacionDTO.getMensajeRespuesta()));
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ALERTA!", rtaCotizacionDTO.getMensajeRespuesta()));
                    }
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                   "ALERTA!", "No se pudo inicializar el WS de Cotización."));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
		"ALERTA!", "El valor del monto ingresado no es mayor que 0 o es un valor no valido."));
        }
    }
}
