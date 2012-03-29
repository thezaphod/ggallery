package ggallery

class Album {

    String id
    String title
    String url
    Email email

    static hasMany = [photos: Photo]
    static mapWith = "simpledb"
}