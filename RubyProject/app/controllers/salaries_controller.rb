class SalariesController < ApplicationController

	def index
		if (params[:employee_id])
			salaries = Salary.joins(:employee).where(:employees => {:id => params[:employee_id]}).all
			# .joins(:salaries)
			render	json: salaries
		else
			salaries = Salary.last(100)
			render	json: salaries
		end
	end

	def create
		s = Salary.new
		s.employee_id = params[:employee_id]
		s.salary = params[:salary]
		s.from_date = params[:from_date]
		s.to_date = params[:to_date]
		s.save
		render json: s
	end
end

