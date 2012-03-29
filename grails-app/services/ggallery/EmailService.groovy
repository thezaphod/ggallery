package ggallery

class EmailService {

    static transactional = false

    def send(email) {

        def mailId = sesMail {
            from "${email.from}"
            to "${email.to}"
            subject "${email.subject}"
            body "${email.text} \n ${email.url}"
        }

        return mailId
    }
}
