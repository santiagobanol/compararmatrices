/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compararmatrices;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Matriz {

    NodoDoble nodoConfiguracion; // en el libro se llama mat

    /**
     * Constructor de la matriz sin elementos
     *
     * @param numeroFilas cantidad de filas de la matriz
     * @param numeroColumnas cantidad de columnas de la matriz
     */
    public Matriz(int numeroFilas, int numeroColumnas) {
        construirNodosCabeza(numeroFilas, numeroColumnas);
    }

    /**
     * Métdo que construye el nodo configuración y nodo cabeza
     *
     * @param numeroFilas
     * @param numeroColumnas
     */
    private void construirNodosCabeza(int numeroFilas, int numeroColumnas) {
        Tripleta t = new Tripleta(numeroFilas, numeroColumnas, 0);
        nodoConfiguracion = new NodoDoble(t);

        //Creo el Nodo cabeza y lo configuro para que sea lista ligada circular
        NodoDoble cabeza = new NodoDoble(null);
        // Referencia circular por la liga filas y la liga columnas
        cabeza.setLigaC(cabeza);
        cabeza.setLigaF(cabeza);

        // conecto con el nodo de configuración
        nodoConfiguracion.setLigaC(cabeza);
        nodoConfiguracion.setLigaF(cabeza);
    }

    /**
     * Método para ingresar los datos de un nuevo registro e insertarlos en la
     * matriz
     *
     * @param fila fila donde se encuentra el dato
     * @param columna columnas donde se encuentra el dato
     * @param valor valor
     */
    public void setCelda(int fila, int columna, double valor) {
        Tripleta nuevoTripletaRegistro = new Tripleta(fila, columna, valor);
        setCelda(nuevoTripletaRegistro);
    }

    /**
     * Método para ingresar los datos de un nuevo registro e insertarlos en la
     * matriz
     *
     * @param t
     */
    public void setCelda(Tripleta t) {
        NodoDoble nuevoNodo = new NodoDoble(t);
        conectarFilas(nuevoNodo);
        conectarColumnas(nuevoNodo);
        int c = (Integer) nodoConfiguracion.getT().getV();
        nodoConfiguracion.getT().setV(c++);
    }

    /**
     * Método que ingresa un nodo recorriendo la lista de las filas
     *
     * @param nuevoNodo
     */
    private void conectarFilas(NodoDoble nuevoNodo) {
        // datos para la comparación
        int filaNuevoNodo = nuevoNodo.getT().getF();
        int columnaNuevoNodo = nuevoNodo.getT().getC();

        // nodos para el recorrido
        NodoDoble cabeza = this.getCabeza();
        NodoDoble ultimo = cabeza;
        NodoDoble nodoRecorrido = cabeza.getLigaF();

        boolean siDebeInsertar = true;

        // Permite posicionar el nodoRecorrido en la fila correcta para ingresar 
        while (nodoRecorrido != null && nodoRecorrido != cabeza) {
            if (nodoRecorrido.getT().getF() < filaNuevoNodo) {
                ultimo = nodoRecorrido;
                nodoRecorrido = nodoRecorrido.getLigaF();
            } else {
                break;
            }
        }

        while (nodoRecorrido != null && nodoRecorrido != cabeza) {
            if (nodoRecorrido.getT().getF() == filaNuevoNodo) {
                if (nodoRecorrido.getT().getC() < columnaNuevoNodo) {
                    ultimo = nodoRecorrido;
                    nodoRecorrido = nodoRecorrido.getLigaF();
                } else if (nodoRecorrido.getT().getC() == columnaNuevoNodo) {
                    siDebeInsertar = false;
                    nodoRecorrido.getT().setV(nuevoNodo.getT().getV());
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        if (siDebeInsertar) {
            ultimo.setLigaF(nuevoNodo);
            nuevoNodo.setLigaF(nodoRecorrido);
        }
    }

    /**
     * Método que ingresa un nodo recorriendo la lista de las columnas
     *
     * @param nuevoNodo
     */
    private void conectarColumnas(NodoDoble nuevoNodo) {
        // datos para la comparación
        int filaNuevoNodo = nuevoNodo.getT().getF();
        int columnaNuevoNodo = nuevoNodo.getT().getC();

        // nodos para el recorrido
        NodoDoble cabeza = getCabeza();
        NodoDoble ultimo = cabeza;
        NodoDoble nodoRecorrido = cabeza.getLigaC();

        boolean siDebeInsertar = true;

        while (nodoRecorrido != null && nodoRecorrido != cabeza) {
            if (nodoRecorrido.getT().getC() < columnaNuevoNodo) {
                ultimo = nodoRecorrido;
                nodoRecorrido = nodoRecorrido.getLigaC();
            } else {
                break;
            }
        }

        while (nodoRecorrido != null && nodoRecorrido != cabeza) {
            if (nodoRecorrido.getT().getC() == columnaNuevoNodo) {
                if (nodoRecorrido.getT().getF() < filaNuevoNodo) {
                    ultimo = nodoRecorrido;
                    nodoRecorrido = nodoRecorrido.getLigaC();
                } else if (nodoRecorrido.getT().getF() == filaNuevoNodo) {
                    siDebeInsertar = false;
                    nodoRecorrido.getT().setV(nuevoNodo.getT().getV());
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        if (siDebeInsertar) {
            ultimo.setLigaC(nuevoNodo);
            nuevoNodo.setLigaC(nodoRecorrido);
        }
    }

    /**
     * Método que devuelve la cabeza
     * @return nodoConfiguracion.getLigaC();
     */
    private NodoDoble getCabeza() {
        return nodoConfiguracion.getLigaC();
    }



    public double calcularPorcentajeCambio(Matriz m2) {
        double cambio = 0;

        int filas = this.nodoConfiguracion.getT().getF();
        int columnas = this.nodoConfiguracion.getT().getC();
        NodoDoble nodoRecorridoM1 = this.nodoConfiguracion.getLigaF().getLigaF();
        NodoDoble nodoRecorridoM2 = m2.nodoConfiguracion.getLigaF().getLigaF();

        for (int fv = 1; fv <= filas; fv++) {
            for (int cv = 1; cv <= columnas; cv++) {
                if (!(nodoRecorridoM1.getT() == null) || !(nodoRecorridoM2.getT() == null)) {
                    double valor1 = 0;
                    double valor2 = 0;
                    int fm1 = (nodoRecorridoM1.getT() == null) ? fv : nodoRecorridoM1.getT().getF();
                    int fm2 = (nodoRecorridoM2.getT() == null) ? fv : nodoRecorridoM2.getT().getF();
                    int cm1 = (nodoRecorridoM1.getT() == null) ? fv : nodoRecorridoM1.getT().getC();
                    int cm2 = (nodoRecorridoM2.getT() == null) ? fv : nodoRecorridoM2.getT().getC();

                    if (fv == fm1 && cv == cm1) {
                        valor1 = (nodoRecorridoM1.getT() == null) ? 0 : (double) nodoRecorridoM1.getT().getV();
                        nodoRecorridoM1 = nodoRecorridoM1.getLigaF();
                    }
                    if (fv == fm2 && cv == cm2) {
                        valor2 = (nodoRecorridoM2.getT() == null) ? 0 : (double) nodoRecorridoM2.getT().getV();
                        nodoRecorridoM2 = nodoRecorridoM2.getLigaF();
                    }
                    if (Math.abs(valor1 - valor2) >= 5) {
                        cambio++;
                    }
                }
            }
        }
        return (cambio * 100) / (filas * columnas);
    }

    public static Matriz crearMatriz(String archivo) throws IOException {
        Matriz matriz = null;
        BufferedReader inputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(archivo));
            String l = inputStream.readLine();
            StringTokenizer st = new StringTokenizer(l);
            int filas = Integer.parseInt(st.nextToken());
            int columnas = Integer.parseInt(st.nextToken());
            matriz = new Matriz(filas, columnas);

            for (int i = 1; i <= filas; i++) {
                inputStream.readLine();
                l = inputStream.readLine();    //Salta 2 veces de linea para saltar los espacios    
                for (int j = 1; j <= columnas; j++) {
                        int valor=Integer.parseInt(String.valueOf(l.charAt(j - 1)));
                        if (valor != 0) {
                            matriz.setCelda(i, j, valor);
                        }
                    }    
            }
                
        } catch (Exception ex) {
            Logger.getLogger(Matriz.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }    
        return matriz;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        // Obtengo la configuración de la matriz, fr y cr y la cantidadValores
        Tripleta configuracion = nodoConfiguracion.getT();
        int fr = configuracion.getF();
        int cr = configuracion.getC();
        // Imprimir una línea con encabezado de las columnas
        cadena.append("\t");
        for (int i = 1; i <= cr; i++) {
            cadena.append(i + "\t");
        }
        cadena.append("\n");

        NodoDoble nodoCabeza = nodoConfiguracion.getLigaF();
        NodoDoble nodoRecorrido = nodoCabeza.getLigaF();
        // Recorrido por una matriz virtual m x n
        for (int fv = 1; fv <= fr; fv++) {
            cadena.append(fv + "\t");
            for (int cv = 1; cv <= cr; cv++) {
                if (nodoRecorrido != null && nodoRecorrido != nodoCabeza) {
                    Tripleta triMo = nodoRecorrido.getT();
                    int ft = triMo.getF();
                    int ct = triMo.getC();
                    if (fv == ft) {
                        if (cv < ct) {
                            cadena.append("0\t");
                        } else if (cv == ct) {
                            Object vt = triMo.getV();
                            if (vt != null) {
                                cadena.append(vt + "\t");
                            } else {
                                cadena.append("ERROR x COLUMNAS!!!!");
                            }
                            nodoRecorrido = nodoRecorrido.getLigaF();
                        } else {
                            cadena.append("ERROR x COLUMNAS se paso!!!!");
                        }
                    } else {
                        cadena.append("0\t");
                    }
                } else {
                    cadena.append("0\t");
                }
            }
            cadena.append("\n");
        }
        return cadena.toString();
    }

}

