package ggallery

import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.impl.rest.httpclient.RestS3Service

class PhotoController {

    // injected
    def aws

    // display image directory
    def index() {

        def c = new AWSCredentials(grailsApplication.config.grails.plugin.aws.credentials.accessKey,
                grailsApplication.config.grails.plugin.aws.credentials.secretKey)

        RestS3Service s3Service = new RestS3Service(c)

        def objs = s3Service.listObjects("ggallery", "", "")

        // create collection for display only
        def album = new Album(title: params.id, url: request.getRequestURL(), photos: new HashSet())

        session.images = [:]

        // enumerate through images
        objs.each() {

            def filePath = it.key

            def pUrl = aws.s3().on("ggallery").publicUrlFor(1.hour, filePath)

            def o = new Photo(filePath: filePath, caption: filePath, url: pUrl)
            o.id = Long.toHexString(System.nanoTime())

            album.photos.add(o)

            // use expireDate as an image ID
            session.images[o.id] = o.url
        }

        [album: album]
    }


}
