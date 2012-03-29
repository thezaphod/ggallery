package ggallery

class AlbumController {

    // injected
    def aws
    def emailService

    def index() { }

    def create() {

        String albumId = System.currentTimeMillis()


        def msg = new Email(from: params.from, to: params.to, subject: params.subject, text: params.email, url: "${getSiteUrl(request)}/album/show?id=")
        msg.save(failOnError: true, flush: true)

        def album = new Album(title: params.title, photos: new HashSet(), email: msg, url: "${getSiteUrl(request)}/album/show?id=")

        // create album
        session.images.each() { filePath ->


            def pubUrl = aws.s3().on("ggallery").publicUrlFor(1.hour, filePath)
            def f = new Photo(filePath: filePath, caption: filePath, url: pubUrl)
            f.save(failOnError: true)
            album.photos.add(f)
        }

        album.save(failOnError: true)

        String albumUrl = "${getSiteUrl(request)}/album/show?id=" + album.id

        msg.url = albumUrl
        msg.save(failOnError: true)

        album.url = albumUrl
        album.save(failOnError: true)

        msg.url = albumUrl
        msg.save(failOnError: true)

        session.album = album
        redirect(action: "show")

        // send e-mail
        emailService.send(msg)
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
