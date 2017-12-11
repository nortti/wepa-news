# Documentation

Wepa News is a news site with a custom control panel for managing news, categories and authors.

#### The gist of it:

- User can browse news by time, popularity and category
- User can click on a piece of news to see it in detail
- Admin can log in to control panel
- Admin can manage news, categories and authors (crud)

#### Schema:

- Authors have a name
- Categories have a name and a switch for whether or not they can be browsed by
- News have a title, lead text, content, publishing time, view count, list of authors, list of categories and an image.

The relationship between news and authors/categories is many-to-many.

#### Usage:

Should be intuitive enough. Login/logout from the top right, user: admin, password: letmein

#### Install & run:

```
git clone git@github.com:nortti/wepa-news.git
```
And open it in your IDE. Or:
```
cd wepa-news
mvn install
mvn spring-boot:run
```

#### Stuff not done due to lack of time:
- System tests
- Views-listing being based on last week only, they are for all time
- When managing categories and authors, showing which news they are used in

#### Bugs and other annoyances:
- Categories and authors are sometimes missing randomly in the select option when opening news editing modal (probably some ajax/mustache.js race condition that could certainly be fixed). Just keep reopening the modal.
- Creating a duplicate category gives a "Something went wrong" message in heroku. There's a nicer message locally but the logic i used is database-specific apparently.
- Same as above, but when trying to delete a category/author that is used in some news.
- Validation failure when editing closes modal and loses changes, very annoying, should move message inside modal
- Long news titles don't line wrap in public view

