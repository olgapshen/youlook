package youlook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.json.simple.JSONObject;
import java.util.*;

import youlook.logic.*;
import youlook.DAL.*;
import youlook.entities.*;

@Controller
public class BaseController
{
	private static final String VIEW_COUNT = "count";
	private static final String VIEW_LIST = "list";
	private static final DigitCounter m_digitCounter = new DigitCounter();
	private static final DataAccess m_dataAccess = new DataAccess();

	@RequestMapping(value = "/countDigits", method = RequestMethod.GET)
	public String countDigits(
		@RequestParam(value="str") String str,
		ModelMap model)
	{
		int count = m_digitCounter.countDigits(str);
		model.addAttribute("count", count);

		m_dataAccess.writeRecord(str, count);

		return VIEW_COUNT;
	}

	@RequestMapping(value = "/listProcessedStrings", method = RequestMethod.GET)
	public String countDigits(ModelMap model)
	{
		ArrayList<Record> records = m_dataAccess.getRecords();
		JSONObject mJSONArray = new JSONObject();

		for (Record record : records) {
    		mJSONArray.put(record.getString(), record.getCount());
		}

		model.addAttribute("list", mJSONArray.toString());

		return VIEW_LIST;
	}
}
