package by.andd3dfx.templateapp.exceptions;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(String id) {
        super("an article", id);
    }
}
