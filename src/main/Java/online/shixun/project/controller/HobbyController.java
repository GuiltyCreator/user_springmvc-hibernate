package online.shixun.project.controller;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.HobbyModel;
import online.shixun.project.model.ResponseData;
import online.shixun.project.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/hobby")
public class HobbyController {

    @Autowired
    private HobbyService hobbyService;

    /**
     * 将请求转发到hobby/add.jsp,供客户端看增加爱好界面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String toAddHobbyFormPage( Model model){

        HobbyModel hobby=new HobbyModel();
        model.addAttribute("hobby",hobby);
        return "/hobby/form";
    }

    /**
     * 查询并分页显示所有的爱好信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String listHobby(Model model, HttpServletRequest request) {
        //获取指定页数（第几页）
        String page = request.getParameter("page");
        //page为空，默认查询第1页数据，import是org.springframework.util.StringUtils
        int pageNo = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        //获取用户分页数据
        PageData<HobbyModel> professionModelPageData = hobbyService.getUserPageData(pageNo, 5);
        model.addAttribute("pageInfo", professionModelPageData);
        return "/hobby/list";
    }

    /**
     * 保存爱好
     * @param params
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData saveHobby(@RequestParam Map<String ,String > params){
        ResponseData responseData=new ResponseData();
        try {
            hobbyService.saveOrUpdate(params);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    /**
     * 进入修改页面
     * @param hobbyId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{hobbyId}/edit")
    public String toEditHobbyFormPage(@PathVariable Long hobbyId,Model model){
        HobbyModel hobbyModel=hobbyService.getHobbyById(hobbyId);
        model.addAttribute("hobby",hobbyModel);
        return "/hobby/form";
    }

    /**
     * 删除爱好
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData deleteHobby(@RequestParam Map<String ,String> params){
        ResponseData responseData=new ResponseData();
        try{
            hobbyService.deleteHobby(Long.parseLong(params.get("id")));
        }catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }


}
