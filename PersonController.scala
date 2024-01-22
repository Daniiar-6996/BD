package controllers

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class PersonController @Inject()(repo: PersonRepository,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * Map для person.
   */
  val personForm: Form[CreatePersonForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "lastname" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "address" -> nonEmptyText,
      "phone" -> nonEmptyText,
      "age" -> number.verifying(min(0), max(140))
    )(CreatePersonForm.apply)(CreatePersonForm.unapply)
  }

  /**
   * The index action.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(personForm))
  }

  /**
   * The add person action.
   *
   * ОБЯЗАТЕЛЬНО АСИНХРОННО 
   */
  def addPerson = Action.async { implicit request =>
    personForm.bindFromRequest.fold(

      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      // Если не было ошибок, то future выполнится .
      person => {
        repo.create(person.name, person.surname, person.lastname, person.address, person.phone, person.age).map { _ =>
          Redirect(routes.PersonController.index).flashing("success" -> "user.created")
        }
      }
    )
  }

  def getPersons = Action.async { implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }
}

/**
 * id сам генерится при создании Person
 */
case class CreatePersonForm(name: String, lastname: String, surname: String, address: String, phone: String, age: Int)
