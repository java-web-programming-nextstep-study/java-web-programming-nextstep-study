//package facade;
//
//import controller.UserController;
//import dto.RequestDto;
//import dto.ResponseDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import webserver.RequestHandler;
//
//public class ExceptionFacade {
//
//    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
//
//    private final UserController userController;
//
//    public ExceptionFacade(UserController userController) {
//        this.userController = userController;
//    }
//
//    public ResponseDto run(RequestDto requestDto) {
//        try {
//            return userController.run(requestDto);
//        } catch (Exception e) {
//            log.error("예외 발생: {}", e.getMessage());
//            return handleException(e);
//        }
//    }
//
//    private ResponseDto handleException(Exception e) {
//        ResponseDto responseDto = new ResponseDto();
//        if(e instanceof IllegalStateException) {
//            responseDto.setException(400, e.getMessage());
//        }
//        else {
//            responseDto.setException(500, e.getMessage());
//        }
//        return responseDto;
//    }
//}
