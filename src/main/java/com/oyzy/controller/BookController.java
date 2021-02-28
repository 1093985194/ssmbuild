package com.oyzy.controller;

import com.oyzy.pojo.Books;
import com.oyzy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/book")
public class BookController {
//    controller 调用 service

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    //查询全部数据，并返回到一个页面
    @RequestMapping(value = "/allBook")
    public String list(Model model){
        List<Books> booksList = bookService.queryAllBooks();
        model.addAttribute("booksList",booksList);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddBook(Books books){
//        bookService.addBook(books);
        return "addBook";
    }
    @RequestMapping("/addBook")
    public String addPaper(Books books) {
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";//重定向 @RequestMapping(value = "/allBook")
    }



    @RequestMapping("/del/{bookID}")
    public String deleteBook(@PathVariable("bookID") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(Model model, int id) {
        Books books = bookService.queryBookById(id);
        System.out.println(books);
        model.addAttribute("book",books );
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book) {
        System.out.println(book);
        bookService.updateBook(book);
        Books books = bookService.queryBookById(book.getBookID());
        model.addAttribute("books", books);
        return "redirect:/book/allBook";
    }

    //查询书
    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName,Model model){
        System.err.println(queryBookName);;
        Books books = bookService.queryBookByName(queryBookName);
        System.err.println(books);;
        List<Books> booksList = new ArrayList<Books>();
        booksList.add(books);
        model.addAttribute("booksList",booksList);
        return "allBook";
    }
}
