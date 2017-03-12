class Employee < ActiveRecord::Base
	has_many :salaries
	has_many :titles
	has_and_belongs_to_many :departments
end
