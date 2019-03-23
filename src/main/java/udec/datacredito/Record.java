
package udec.datacredito;

/**
 *
 * @author Jonathan
 */
public class Record {
    
    private int codigo;
    
    private String empresa;
    
    private boolean estado;
    
    private float valor;

    public Record(int codigo, String empresa, boolean estado, float valor) {
        this.codigo = codigo;
        this.empresa = empresa;
        this.estado = estado;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    
    
}//Record
