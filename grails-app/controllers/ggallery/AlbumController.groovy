package ggallery

class AlbumController {

    // injected
    def aws

    def index() { }

    def create() {

        String albumId = System.currentTimeMillis()


        def email = new Email(from: params.from, to: params.to, subject: params.subject, text: params.email, url: "${getSiteUrl(request)}/album/show?id=")
        email.save(failOnError: true, flush: true)

        def album = new Album(title: params.title, photos: new HashSet(), email: email, url: "${getSiteUrl(request)}/album/show?id=")

        // create album
        session.images.each() { filePath ->


            def pubUrl = aws.s3().on("ggallery").publicUrlFor(1.hour, filePath)
            def f = new Photo(filePath: filePath, caption: filePath, url: pubUrl)
            f.save(failOnError: true)
            album.photos.add(f)
        }

        album.save(failOnError: true)

        String albumUrl = "${getSiteUrl(request)}/album/show?id=" + album.id

        email.url = albumUrl
        email.save(failOnError: true)

        album.url = albumUrl
        album.save(failOnError: true)

        email.url = albumUrl
        email.save(failOnError: true)

        session.album = album
        redirect(action: "show")

        print email.url
        print album.id
    }

    def show() {

        def album

        if (params.id != null) {
            album = Album.findById(params.id)
        } else {
            album = session.album
        }

        [album: album]
    }


    def static getSiteUrl(request) {

        javax.servlet.http.HttpServletRequest.metaClass.getSiteUrl = {

            return (delegate.scheme + "://" + delegate.serverName + ":" + delegate.serverPort + delegate.getContextPath())

        }
        return request.getSiteUrl()
    }
}
