package online.shixun.project.controller;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.ProfessionModel;
import online.shixun.project.model.ResponseData;
import online.shixun.project.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "profession")
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    /**
     * 查询并分页显示所有的职业信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String listProfession(Model model, HttpServletRequest request) {
        //获取指定页数（第几页）
        String page = request.getParameter("page");
        //page为空，默认查询第1页数据，import是org.springframework.util.StringUtils
        int pageNo = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        //获取用户分页数据
        PageData<ProfessionModel> professionModelPageData = professionService.getUserPageData(pageNo, 5);
        model.addAttribute("pageInfo", professionModelPageData);
        return "/profession/list";
    }


    /**
     * 将请求转发到profession/add.jsp,供客户端看增加职业界面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String addProfession( Model model){

        ProfessionModel profession=new ProfessionModel();
        model.addAttribute("profession",profession);
        return "/profession/form";
    }

    /**
     * 保存职业
     * @param params
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData saveProfession(@RequestParam Map<String ,String > params){

        ResponseData responseData=new ResponseData();
        try {
            professionService.saveOrUpdate(params);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    /**
     * 进入修改页面
     * @param professionId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{professionId}/edit")
    public String toEditHobbyFormPage(@PathVariable Long professionId, Model model){
        ProfessionModel professionModel=professionService.getProfessionById(professionId);
        model.addAttribute("profession",professionModel);
        return "/profession/form";
    }

    /**
     * 删除职业
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData deleteHobby(@RequestParam Map<String ,String> params){
        ResponseData responseData=new ResponseData();
        try{
            professionService.deleteProfession(Long.parseLong(params.get("id")));
        }catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }


}
