class TitlesController < ApplicationController
	def index
		if (params[:employee_id])
			title = Title.joins(:employee).where(:employees => {:id => params[:employee_id]}).all
			# .joins(:salaries)
			render	json: title
		else
			title = Title.last(100)
			render	json: title
		end
	end

	def create
		t = Title.new
		t.employee_id = params[:employee_id]
		t.title = params[:title]
		t.from_date = params[:from_date]
		t.to_date = params[:to_date]
		t.save
		render json: t
	end
end
