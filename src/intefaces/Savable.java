// **** SET PACKAGE ****\\
package intefaces;

// Savable interface, when classes extend this they must implement a save and load method that both accept a path as a parameter
public interface Savable {
    //this interface is implemented by the MessageModel and UserModel
    void save(String path);
    void load(String path);
}
