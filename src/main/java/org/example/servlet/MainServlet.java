package org.example.servlet;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String PATHAPIPOSTS = "/api/posts";
  private static final String PATHDELIMETER = "/";
  private static final String PATHMATHCER = "/\\d+";


  private PostController controller;

  @Override
  public void init() {
    //final var repository = new PostRepository();
    final var repository = PostRepository.getInstance();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(PATHAPIPOSTS)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(PATHAPIPOSTS + PATHMATHCER)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(PATHDELIMETER) + 1));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST) && path.equals(PATHAPIPOSTS)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(PATHAPIPOSTS + PATHMATHCER)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(PATHDELIMETER) + 1));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

