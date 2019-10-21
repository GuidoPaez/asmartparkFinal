package Room.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "empresa",indices = {@Index("id")})
public class Empresa {
    private String nombre;
    private String RUT;
    private String DV;
    @PrimaryKey
    @NonNull
    private int id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRUT() {
        return RUT+"-"+DV;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getDV(){
        return DV;
    }

    public void setDV(String DV){
        this.DV = DV;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}