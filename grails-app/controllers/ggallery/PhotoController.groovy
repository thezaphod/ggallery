package ggallery

import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.impl.rest.httpclient.RestS3Service

class PhotoController {

    // injected
    def aws

    // add/remove file from album
    def toggle() {
        print params

        if (session.images == null) session.images = []

        if (session.images.contains(params.file)) {
            session.images.remove[params.file]
        } else {
            session.images.add(params.file)
        }

        render(status: 200, text: 'done')
    }


    // display image directory
    def index() {

        session.images = null
        session.album = null

        def c = new AWSCredentials(grailsApplication.config.grails.plugin.aws.credentials.accessKey,
                grailsApplication.config.grails.plugin.aws.credentials.secretKey)

        RestS3Service s3Service = new RestS3Service(c)

        def objs = s3Service.listObjects("ggallery", "", "")

        // create Album for display only
        def album = new Album(title: params.id, url: request.getRequestURL(), photos: new HashSet())

        // enumerate through images
        objs.each() {

            def filePath = it.key

            def pUrl = aws.s3().on("ggallery").publicUrlFor(1.hour, filePath)

            def o = new Photo(filePath: filePath, caption: filePath, url: pUrl)
            o.id = Long.toHexString(System.nanoTime())

            album.photos.add(o)
        }

        [album: album]
    }


}
