package com.kk.dialer.sql;

public class Contact {
    int _id;
    String _name;
    String _phone_number;
    byte[] _image;

    public Contact(int _id, String _name, String _phone_number, byte[] _image) {
        this._id = _id;
        this._name = _name;
        this._phone_number = _phone_number;
        this._image = _image;
    }

    public Contact() {
    }

    public Contact(String name, String _phone_number, byte[] _image) {
        this._name = name;
        this._phone_number = _phone_number;
        this._image = _image;

    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPhoneNumber() {
        return this._phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }

}  